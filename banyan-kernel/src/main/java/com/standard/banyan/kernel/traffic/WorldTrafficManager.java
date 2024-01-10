package com.standard.banyan.kernel.traffic;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.tiansu.nts.rcs.event.amr.extend.AmrAppliedTrafficEvent;
import com.tiansu.nts.rcs.kernel.cfg.TrafficConfig;
import com.tiansu.nts.rcs.kernel.traffic.shape.ConvertStep2Shapes;
import com.tiansu.nts.rcs.kernel.traffic.shape.Shape;
import com.tiansu.nts.rcs.model.Resource;
import com.tiansu.nts.rcs.model.Step;
import com.tiansu.nts.rcs.util.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author dingchengfeng
 * @description 世界交通管理器
 * @date 2023/07/25
 */
@Component
@Slf4j
public class WorldTrafficManager implements TrafficManager {

    @javax.annotation.Resource
    private TrafficConfig trafficConfig;

    private volatile boolean stopFlag = false;

    private Thread trafficThread = null;

    /**
     * 机器人资源占用表
     */
    private Table<String, Resource.GlobalId, List<Shape>> resourceHoldTable;

    /**
     * 机器人资源申请表
     */
    private Table<String, Resource.GlobalId, List<Shape>> resourceApplyTable;

    public void init() {
        resourceHoldTable = HashBasedTable.create();
        resourceApplyTable = HashBasedTable.create();
        // 这里手动创建线程是为了方便控制线程执行，降低cpu使用率
        trafficThread = new Thread(new TrafficThread(), "Traffic-thread");
    }

    @Override
    public void start() {
        init();
        trafficThread.start();
    }

    @Override
    public void stop() {
        stopFlag = true;
    }

    @Override
    public synchronized void applyTrafficForSteps(List<Step> steps, AmrInfo amrInfo) {
        log.info("申请交管:steps={},amrInfo={}", steps, amrInfo);

        for (Step step : steps) {
            List<Shape> shapes = ConvertStep2Shapes.convert(step, amrInfo);
            resourceApplyTable.put(amrInfo.getAmrId(), step.getResourceId(), shapes);
        }
        LockSupport.unpark(trafficThread);
    }

    @Override
    public synchronized void releaseTraffic(List<Resource.GlobalId> resourceIds, String amrId) {
        log.info("释放资源:resourceIds={},amrId={}", resourceIds, amrId);

        for (Resource.GlobalId resourceId : resourceIds) {
            resourceHoldTable.remove(amrId, resourceId);
        }
        LockSupport.unpark(trafficThread);
    }

    @Override
    public synchronized void releaseAmrTraffic(String amrId) {
        log.info("清楚车辆占用:amrId={}", amrId);
        Map<Resource.GlobalId, List<Shape>> row = resourceHoldTable.row(amrId);
        for (Resource.GlobalId resourceId : row.keySet()) {
            resourceHoldTable.remove(amrId, resourceId);
        }
        LockSupport.unpark(trafficThread);
    }

    @Override
    public synchronized void cancelApply(String amrId) {
        log.info("取消资源申请:amrId={}", amrId);
        Map<Resource.GlobalId, List<Shape>> row = resourceApplyTable.row(amrId);
        for (Resource.GlobalId resourceId : row.keySet()) {
            resourceApplyTable.remove(amrId, resourceId);
        }
    }

    private class TrafficThread implements Runnable {

        @Override
        public void run() {
            try {
                while (!stopFlag) {
                    LockSupport.park();
                    tryAllocateResource();
                }
            } catch (Exception e) {
                log.error("交管线程执行异常:", e);
            }
        }
    }

    private synchronized void tryAllocateResource() {
        Set<String> applyResourceAmrIds = new HashSet<>(resourceApplyTable.rowKeySet());

        for (String amrId : applyResourceAmrIds) {
            tryAllocateResourceForAmr(amrId);
        }
    }

    private synchronized void tryAllocateResourceForAmr(String amrId) {
        Map<Resource.GlobalId, List<Shape>> amrApplyShapes = resourceApplyTable.row(amrId);
        Set<Shape> applyShapes = new HashSet<>();
        Collection<List<Shape>> values = amrApplyShapes.values();
        for (List<Shape> shapes : values) {
            applyShapes.addAll(shapes);
        }

        if (checkCollision(amrId, applyShapes)) {
            return;
        }

        Set<Map.Entry<Resource.GlobalId, List<Shape>>> resourceShapes = amrApplyShapes.entrySet();
        for (Map.Entry<Resource.GlobalId, List<Shape>> resourceShape : resourceShapes) {
            // 占用资源
            resourceHoldTable.put(amrId, resourceShape.getKey(), resourceShape.getValue());
        }
        ApplicationContextUtils.asyncPublishEvent(new AmrAppliedTrafficEvent(this, amrId, amrApplyShapes.keySet()));

        HashSet<Resource.GlobalId> resourceSet = new HashSet<>(amrApplyShapes.keySet());
        for (Resource.GlobalId resourceId : resourceSet) {
            // 移除申请
            resourceApplyTable.remove(amrId, resourceId);
        }
    }

    /**
     * 检测申请的图形和其他机器人占用的图形是否碰撞
     *
     * @param amrId       机器人id
     * @param applyShapes 申请的图形
     * @return true:碰撞；false:不碰撞
     */
    private boolean checkCollision(String amrId, Set<Shape> applyShapes) {
        Set<Table.Cell<String, Resource.GlobalId, List<Shape>>> cells = resourceHoldTable.cellSet();
        for (Table.Cell<String, Resource.GlobalId, List<Shape>> cell : cells) {
            if (amrId.equals(cell.getRowKey())) {
                // 和自己占用的资源不用检测
                continue;
            }
            for (Shape holdShape : cell.getValue()) {
                for (Shape applyShape : applyShapes) {
                    if (applyShape.getResourceId().equals(holdShape.getResourceId())) {
                        return true;
                    }
                    if (applyShape.collision(holdShape)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
