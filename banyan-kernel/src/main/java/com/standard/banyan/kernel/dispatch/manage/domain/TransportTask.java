package com.standard.banyan.kernel.dispatch.manage.domain;

import cn.hutool.core.collection.CollUtil;
import com.tiansu.nts.rcs.entity.dto.ActionDTO;
import com.tiansu.nts.rcs.enums.state.TaskStatusEnum;
import com.tiansu.nts.rcs.imap.manage.resource.pose.Station;
import com.tiansu.nts.rcs.kernel.execute.AmrExecutor;
import com.tiansu.nts.rcs.kernel.execute.AmrExecutorManager;
import com.tiansu.nts.rcs.model.Action;
import com.tiansu.nts.rcs.task.entity.SubTaskInfoDTO;
import com.tiansu.nts.rcs.task.entity.TaskInfoDTO;
import com.tiansu.nts.rcs.util.ApplicationContextUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 任务
 *
 * @author dingchengfeng
 * @description 任务
 * @date 2023/07/19
 */
@Getter
@Setter
public class TransportTask implements Comparable<TransportTask> {

    private final String taskId;

    private final Type type;

    private final Integer priority;

    private final Instant createTime;

    /**
     * 预约的机器人
     */
    private final String intendedAmrId;

    private TaskStatusEnum taskStatus;

    private final List<DriverTask> driverTasks;

    @Getter
    private Integer currentDriverTaskIndex = -1;

    public TransportTask(TaskInfoDTO taskInfoDTO) {
        this.taskId = taskInfoDTO.getTaskId().toString();
        this.type = Type.codeOf(taskInfoDTO.getType());
        this.createTime = taskInfoDTO.getCreateTime().toInstant(ZoneOffset.UTC);
        this.intendedAmrId = taskInfoDTO.getAmrId();
        this.priority = taskInfoDTO.getPriority();
        this.taskStatus = TaskStatusEnum.codeOf(taskInfoDTO.getStatus());
        this.driverTasks = new ArrayList<>();
        for (SubTaskInfoDTO subTaskInfoDTO : taskInfoDTO.getSubTaskInfoDTOS()) {
            if (TaskStatusEnum.FINISHED.getCode().equals(subTaskInfoDTO.getStatus())) {
                currentDriverTaskIndex = taskInfoDTO.getSubTaskInfoDTOS().indexOf(subTaskInfoDTO);
            }
            driverTasks.add(new DriverTask(subTaskInfoDTO.getSubTaskId(), subTaskInfoDTO.getStationObject(),
                convertAction(subTaskInfoDTO.getActions())));
        }
        if (!TaskStatusEnum.TO_BE_ASSIGNED.equals(taskStatus)) {
            AmrExecutorManager amrExecutorManager = ApplicationContextUtils.getBean(AmrExecutorManager.class);
            AmrExecutor amrExecutor = amrExecutorManager.getAmrExecutor(taskInfoDTO.getAmrId());
            amrExecutor.bindTask(this);
        }
    }

    private List<Action> convertAction(List<ActionDTO> actionDTOs) {
        List<Action> actions = new ArrayList<>();
        if (CollUtil.isNotEmpty(actionDTOs)) {
            for (ActionDTO actionDTO : actionDTOs) {
                if (!TaskStatusEnum.FINISHED.getCode().equals(actionDTO.getStatus())) {
                    actions.add(new Action(null, actionDTO));
                }
            }
        }
        return actions;
    }


    /**
     * 是否为停靠任务
     *
     * @return true 是，
     */
    public boolean isParkTask() {
        return Type.PARK.equals(type);
    }

    /**
     * 是否为充电任务
     *
     * @return true 是，
     */
    public boolean isChargeTask() {
        return Type.CHARGE.equals(type);
    }

    /**
     * 是否为工作任务
     *
     * @return true 是，
     */
    public boolean isWorkTask() {
        return Type.WORK.equals(type);
    }

    public Station getTaskFirstStation() {
        return driverTasks.get(0).getStation();
    }

    public DriverTask getCurrentDriverTask() {
        if (currentDriverTaskIndex < 0 || currentDriverTaskIndex >= driverTasks.size()) {
            return null;
        }
        return driverTasks.get(currentDriverTaskIndex);
    }

    public DriverTask nextDriverTask() {
        if (hasNextDriverTask()) {
            return driverTasks.get(++currentDriverTaskIndex);
        } else {
            return null;
        }
    }

    public boolean hasNextDriverTask() {
        return currentDriverTaskIndex + 1 < driverTasks.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransportTask transportTask = (TransportTask)o;
        return taskId.equals(transportTask.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }

    @Override
    public int compareTo(TransportTask o) {
        return priority.compareTo(o.getPriority());
    }

    @Getter
    public enum Type {
        /**
         * 工作
         */
        WORK(0),
        /**
         * 充电
         */
        CHARGE(1),
        /**
         * 停靠
         */
        PARK(2);

        Type(Integer code) {
            this.code = code;
        }

        /**
         * 表中存的枚举的数字值
         */
        private final Integer code;

        public static Type codeOf(Integer value) {
            return Arrays.stream(values()).filter(t -> t.getCode().equals(value)).findFirst().orElse(null);
        }

    }
}
