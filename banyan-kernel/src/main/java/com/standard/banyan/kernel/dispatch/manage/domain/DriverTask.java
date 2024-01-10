package com.standard.banyan.kernel.dispatch.manage.domain;

import cn.hutool.core.collection.CollUtil;
import com.tiansu.nts.rcs.enums.state.ActionStatusEnum;
import com.tiansu.nts.rcs.imap.manage.MapManager;
import com.tiansu.nts.rcs.imap.manage.resource.pose.Station;
import com.tiansu.nts.rcs.kernel.route.Route;
import com.tiansu.nts.rcs.model.*;
import com.tiansu.nts.rcs.util.ApplicationContextUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dingchengfeng
 * @description 驱动任务
 * @date 2023/07/25
 */
@Getter
@Setter
public class DriverTask {

    /**
     * 任务站点
     */
    private final Station station;

    /**
     * 子任务id
     */
    private final String subTaskId;

    /**
     * 跨地图之后的衍生子任务id
     */
    private String subTaskIdAfterCrossMap;

    /**
     * 跨地图步骤索引
     */
    private Integer crossMapIndex = null;

    /**
     * 终点动作集合
     */
    private List<Action> finalActions;

    /**
     * 路由
     */
    private Route route;

    /**
     * 移动任务
     */
    private List<MovementTask> movementTasks = new ArrayList<>();

    /**
     * 已经下发的索引
     */
    private Integer currentMovementTaskIndex = -1;

    /**
     * 移动任务是否完成
     */
    private boolean isMovementFinish = false;

    /**
     * 交管段最小长度
     */
    public static final double MIN_LENGTH = 1.0D;

    /**
     * 查询任务站点
     *
     * @return 任务站点
     */
    public Station getStation() {
        return station;
    }

    public DriverTask(String subTaskId, Station station, List<Action> finalActions) {
        this.subTaskId = subTaskId;
        this.station = station;
        this.finalActions = finalActions;
    }

    /**
     * 拆分路由
     */
    public void splitRoute() {
        List<Step> steps = route.getSteps();
        List<AmrAppendCommand> movementCommands = new ArrayList<>();
        Iterator<Step> stepIterator = steps.iterator();
        AmrAppendCommand preAppendCommand = null;
        Flow postFlow = null;
        // 处理流程
        while (stepIterator.hasNext()) {
            Step step = stepIterator.next();
            if (step.isEdgeStep()) {
                assert preAppendCommand != null;
                // 边的前置流程绑定到前一个节点步骤上
                preAppendCommand.setPreFlow(step.getPreFlow());
                postFlow = step.getPostFlow();
            }
            if (stepIterator.hasNext()) {
                AmrAppendCommand amrAppendCommand = new AmrAppendCommand(step);
                // 前一条边的后置流程绑定到此节点步骤上
                if (step.isNodeStep()) {
                    amrAppendCommand.setPostFlow(postFlow);
                }
                if (postFlow != null && Flow.LIFT_INNER_CALL.equals(postFlow.getFlowName())) {
                    crossMapIndex = steps.indexOf(step);
                    this.subTaskIdAfterCrossMap = this.subTaskId + "_" + crossMapIndex;
                }
                movementCommands.add(amrAppendCommand);
                preAppendCommand = amrAppendCommand;
            } else {
                movementCommands.add(new AmrAppendCommand(step, finalActions));
            }
        }

        mergeMovementCommand(movementCommands);
    }

    private void mergeMovementCommand(List<AmrAppendCommand> movementCommands) {
        if (movementCommands.size() <= 1) {
            List<MovementCommand> movementCommandList = movementCommands.stream().map(t -> (MovementCommand)t)
                .collect(Collectors.toList());
            movementTasks.add(new MovementTask(movementCommandList, subTaskId));
            return;
        }

        MapManager mapManager = ApplicationContextUtils.getBean(MapManager.class);

        //标记禁止停车的点和必须停车的点
        for (int i = 0; i < movementCommands.size() - 1; i++) {
            AmrAppendCommand amrAppendCommand = movementCommands.get(i);
            AmrAppendCommand nextAmrAppendCommand = movementCommands.get(i + 1);
            // 下一步是双向路的点禁止停车
            if (nextAmrAppendCommand.getStep().isEdgeStep() && mapManager.existReverseEdge(
                nextAmrAppendCommand.getStep().getEdge())) {
                amrAppendCommand.setForbiddenStopFlag(true);
            }

            if (amrAppendCommand.getPreFlow() != null || amrAppendCommand.getPostFlow() != null) {
                amrAppendCommand.setMustStopFlag(true);
            }
        }

        AmrAppendCommand amrAppendCommand = movementCommands.get(0);
        MovementTask movementTask = new MovementTask(subTaskId);
        movementTask.addMovementCommand(amrAppendCommand);

        //  合并步骤
        for (int i = 1; i < movementCommands.size(); i++) {
            String orderId = subTaskId;
            if (crossMapIndex != null && i > crossMapIndex) {
                orderId = subTaskIdAfterCrossMap;
            }
            amrAppendCommand = movementCommands.get(i);
            if (amrAppendCommand.isMustStopFlag()) {
                movementTask.addMovementCommand(amrAppendCommand);
                movementTasks.add(movementTask);
                movementTask = new MovementTask(orderId);
                continue;
            }

            AmrAppendCommand preAmrAppendCommand = movementCommands.get(i - 1);
            if (preAmrAppendCommand.isForbiddenStopFlag()) {
                movementTask.addMovementCommand(amrAppendCommand);
                continue;
            }

            if (amrAppendCommand.getStep().isNodeStep()) {
                movementTask.addMovementCommand(amrAppendCommand);
                continue;
            }

            if (movementTask.getLength() < MIN_LENGTH) {
                movementTask.addMovementCommand(amrAppendCommand);
            } else {
                movementTasks.add(movementTask);
                movementTask = new MovementTask(orderId);
                movementTask.addMovementCommand(amrAppendCommand);
            }
        }
        movementTasks.add(movementTask);

    }

    public MovementTask nextMovementTask() {
        if (currentMovementTaskIndex + 1 >= movementTasks.size()) {
            return null;
        } else {
            return movementTasks.get(++currentMovementTaskIndex);
        }
    }

    public boolean hasNextMovementTask() {
        return currentMovementTaskIndex + 1 < movementTasks.size();
    }

    public MovementTask getCurrentMovementTask() {
        if (currentMovementTaskIndex < 0 || currentMovementTaskIndex >= movementTasks.size() - 1) {
            return null;
        }
        return movementTasks.get(currentMovementTaskIndex);
    }

    public boolean isFinished() {
        if (CollUtil.isNotEmpty(finalActions)) {
            for (Action action : finalActions) {
                if (!ActionStatusEnum.FINISHED.equals(action.getState())) {
                    return false;
                }
            }
        }
        return isMovementFinish;
    }


    public enum State {

        /**
         * 初始化
         */
        PRISTINE,
        /**
         * 移动中
         */
        TRAVELLING,
        /**
         * 动作中
         */
        OPERATING,
        /**
         * 完成
         */
        FINISHED,
        /**
         * 失败
         */
        FAILED
    }

}
