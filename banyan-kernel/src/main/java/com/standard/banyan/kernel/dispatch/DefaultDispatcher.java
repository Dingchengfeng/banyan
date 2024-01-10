package com.standard.banyan.kernel.dispatch;

import cn.hutool.core.collection.CollectionUtil;
import com.tiansu.nts.rcs.imap.manage.MapManager;
import com.tiansu.nts.rcs.imap.manage.resource.pose.Station;
import com.tiansu.nts.rcs.kernel.cfg.DispatchConfig;
import com.tiansu.nts.rcs.kernel.dispatch.algorithm.MatchAlgo;
import com.tiansu.nts.rcs.kernel.dispatch.manage.TaskManager;
import com.tiansu.nts.rcs.kernel.dispatch.manage.domain.TransportTask;
import com.tiansu.nts.rcs.kernel.execute.AmrExecutor;
import com.tiansu.nts.rcs.kernel.execute.AmrExecutorManager;
import com.tiansu.nts.rcs.kernel.execute.manage.Amr;
import com.tiansu.nts.rcs.kernel.execute.manage.AmrManager;
import com.tiansu.nts.rcs.kernel.route.Router;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author dingchengfeng
 * @description 默认任务分配器
 * @date 2023/07/19
 */
@Component
@Slf4j
public class DefaultDispatcher implements Dispatcher {
    @Resource
    private TaskManager taskManager;

    @Resource
    private AmrManager amrManager;

    @Resource
    private MatchAlgo matchAlgo;

    @Resource
    private Router router;

    @Resource
    private MapManager mapManager;

    @Resource
    private DispatchConfig dispatchConfig;

    @Resource
    private AmrExecutorManager amrExecutorManager;

    private volatile boolean stopFlag = true;

    private static final String VIRTUAL_AMR_PREFIX = "VA";

    private static final String VIRTUAL_TASK_PREFIX = "VT";

    private Thread dispatcherThread = null;

    public void dispatch() {
        // 1、分配工作任务
        dispatchWorkTask();
        // 2、分配充电任务
        dispatchChargeTask();
        // 3、分配停靠任务
        dispatchParkTask();
    }

    private void dispatchWorkTask() {
        // 分配有预约的任务
        assignReservedTasks();
        // 分配没有预约的任务
        assignFreeTasks();
    }

    private void dispatchChargeTask() {
        // 查询需要充电的车
        List<Amr> chargeAbleAmrs = amrManager.findChargeAbleAmr();
        // 按电量排序
        chargeAbleAmrs.sort(Comparator.comparing(t -> t.getStateMessage().getBatteryState().getBatteryCharge()));
        // 查询所有充电站
        Set<Station> stations = mapManager.getStations(Station.Type.CHARGE);
        List<Station> occupiedStations = amrManager.findOccupiedChargeStations();
        // 去掉已占用的充电站
        occupiedStations.forEach(stations::remove);
        for (Amr amr : chargeAbleAmrs) {
            // 找到离车最近的充电站
            Station nearestEndStation = router.findNearestEndStation(amr.getAmrId(), amr.getCurrentPose(), stations);
            if (nearestEndStation != null) {
                // 创建充电任务
                Long chargeTaskId = taskManager.createChargeTask(amr, nearestEndStation);
                stations.remove(nearestEndStation);
                assign(amr.getAmrId(), chargeTaskId.toString());
            }
        }
    }

    private void dispatchParkTask() {
        // 查询需要充电的车
        List<Amr> parkAbleAmrs = amrManager.findParkAbleAmr();
        // 查询所有充电站
        Set<Station> stations = mapManager.getStations(Station.Type.PARK);
        List<Station> occupiedStations = amrManager.findOccupiedParkStations();
        // 去掉已占用的充电站
        occupiedStations.forEach(stations::remove);
        for (Amr amr : parkAbleAmrs) {
            // 找到离车最近的充电站
            Station nearestEndStation = router.findNearestEndStation(amr.getAmrId(), amr.getCurrentPose(), stations);
            if (nearestEndStation != null) {
                // 创建充电任务
                Long parkTaskId = taskManager.createParkTask(amr, nearestEndStation);
                stations.remove(nearestEndStation);
                assign(amr.getAmrId(), parkTaskId.toString());
            }
        }
    }

    /**
     * @param amrList           机器人列表
     * @param transportTaskList 任务列表
     * @return 匹配关系 key:amrId;value:taskId;
     */
    private Map<String, String> match(List<Amr> amrList, List<TransportTask> transportTaskList) {
        if (amrList.isEmpty() || transportTaskList.isEmpty()) {
            return new HashMap<>(1);
        }
        //初始化成本方阵
        double[][] costMatrix = initCostMatrix(amrList, transportTaskList);

        List<String> amrIdList = amrList.stream().map(Amr::getAmrId).collect(Collectors.toList());
        List<String> taskIdList = transportTaskList.stream().map(TransportTask::getTaskId).collect(Collectors.toList());
        fillVirtualVertex(amrList, transportTaskList, amrIdList, taskIdList);

        Map<String, String> match = matchAlgo.match(amrIdList, taskIdList, costMatrix);
        for (Map.Entry<String, String> entry : match.entrySet()) {
            if (entry.getKey().contains(VIRTUAL_AMR_PREFIX) || entry.getValue().contains(VIRTUAL_TASK_PREFIX)) {
                match.remove(entry.getKey());
            }
        }
        return match;
    }

    private void fillVirtualVertex(List<Amr> amrList, List<TransportTask> transportTaskList, List<String> amrIdList,
        List<String> taskIdList) {
        if (transportTaskList.size() < amrList.size()) {
            // 任务比车少
            for (int i = transportTaskList.size(); i < amrList.size(); i++) {
                taskIdList.add(VIRTUAL_TASK_PREFIX + i);
            }
        } else {
            // 任务不比车少
            for (int i = amrList.size(); i < transportTaskList.size(); i++) {
                amrIdList.add(VIRTUAL_AMR_PREFIX + i);
            }
        }
    }

    private double[][] initCostMatrix(List<Amr> amrList, List<TransportTask> transportTaskList) {
        int matrixLength = Math.max(amrList.size(), transportTaskList.size());
        double[][] costMatrix = new double[matrixLength][matrixLength];
        for (int i = 0; i < matrixLength; i++) {
            Arrays.fill(costMatrix[i], Double.MAX_VALUE);
        }
        for (int i = 0; i < amrList.size(); i++) {
            for (int j = 0; j < transportTaskList.size(); j++) {
                costMatrix[i][j] = calculateCost(amrList.get(i), transportTaskList.get(j));
            }
        }
        return costMatrix;
    }

    private Double calculateCost(Amr amr, TransportTask transportTask) {
        Double cost = router.calculateCost(amr.getAmrId(), amr.getCurrentPose(), transportTask.getTaskFirstStation());
        if (Objects.isNull(cost)) {
            cost = Double.MAX_VALUE;
        }

        return cost;
    }

    private void bindAmrAndTask(Map<String, String> match) {
        // 绑定任务
        for (Map.Entry<String, String> entry : match.entrySet()) {
            assign(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 任务分配给机器人
     *
     * @param amrId  机器人id
     * @param taskId 任务id
     */
    private void assign(String amrId, String taskId) {
        amrExecutorManager.getAmrExecutor(amrId).assignTask(taskManager.queryTask(taskId));
    }

    private void assignReservedTasks() {
        // 查询已经预约机器人的可分配的任务
        List<TransportTask> transportTasks = taskManager.findReservedDispatchableTask();
        transportTasks.sort(
            Comparator.comparing(TransportTask::getPriority).thenComparing(TransportTask::getCreateTime));
        for (TransportTask task : transportTasks) {
            AmrExecutor amrExecutor = amrExecutorManager.getAmrExecutor(task.getIntendedAmrId());
            if (amrExecutor.dispatchable()) {
                // 原则上指定的车一定是可以接此任务的，也可以检查一下车到任务起点是否可达
                assign(task.getIntendedAmrId(), task.getTaskId());
            }
        }
    }

    private void assignFreeTasks() {
        // 1、查询可分配的任务
        List<TransportTask> transportTasks = taskManager.findFreeDispatchableTask();
        // 2、查询可以接任务的车
        List<Amr> dispatchableAmrs = amrManager.findDispatchableAmr();
        if(CollectionUtil.isEmpty(transportTasks) || CollectionUtil.isEmpty(dispatchableAmrs)){
            return;
        }
        // 3、任务多的话，按规则排序
        if (dispatchableAmrs.size() < transportTasks.size()) {
            transportTasks.sort(
                Comparator.comparing(TransportTask::getPriority).thenComparing(TransportTask::getCreateTime));
            transportTasks = transportTasks.subList(0, dispatchableAmrs.size());
        }

        Map<String, String> match = match(dispatchableAmrs, transportTasks);
        bindAmrAndTask(match);
    }

    @Override
    public void start() {
        // 启动任务分配线程
        init();
        dispatcherThread.start();
        this.stopFlag = false;
    }

    private void init() {
        this.dispatcherThread = new Thread(new DispatcherThread(), "dispatcher");
    }

    @Override
    public void stop() {
        this.stopFlag = true;
    }

    private class DispatcherThread implements Runnable {

        @Override
        public void run() {

            while (!stopFlag) {
                try {
                    Integer interval = dispatchConfig.dispatchInterval();
                    Thread.sleep(TimeUnit.SECONDS.toMillis(interval));
                    dispatch();
                } catch (Exception e) {
                    log.error("分配线程执行异常:", e);
                }
            }

        }
    }
}
