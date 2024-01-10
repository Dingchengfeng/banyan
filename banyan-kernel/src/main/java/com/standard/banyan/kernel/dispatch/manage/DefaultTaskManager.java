package com.standard.banyan.kernel.dispatch.manage;

import cn.hutool.core.util.IdUtil;
import com.tiansu.nts.rcs.entity.dto.ActionDTO;
import com.tiansu.nts.rcs.enums.state.TaskStatusEnum;
import com.tiansu.nts.rcs.event.task.TaskSuspendEvent;
import com.tiansu.nts.rcs.imap.manage.Continent;
import com.tiansu.nts.rcs.imap.manage.MapManager;
import com.tiansu.nts.rcs.imap.manage.resource.pose.Station;
import com.tiansu.nts.rcs.kernel.dispatch.manage.domain.TransportTask;
import com.tiansu.nts.rcs.kernel.execute.manage.Amr;
import com.tiansu.nts.rcs.model.Action;
import com.tiansu.nts.rcs.task.entity.SubTaskInfoDTO;
import com.tiansu.nts.rcs.task.entity.TaskInfoDTO;
import com.tiansu.nts.rcs.task.entity.TaskSearchDTO;
import com.tiansu.nts.rcs.task.service.TaskService;
import com.tiansu.nts.rcs.util.ApplicationContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认任务管理器
 *
 * @author dingchengfeng
 * @description 任务管理器
 * @date 2023/07/12
 */
@Component
public class DefaultTaskManager implements TaskManager {
    @Resource
    private TaskService taskService;
    @Resource
    private MapManager mapManager;

    private final Map<String, TransportTask> taskMap = new ConcurrentHashMap<>(32);

    @Override
    public void init() {
        // 查询不处于终态的订单,加载任务信息到taskMap
        List<TaskInfoDTO> noEndTaskList = taskService.getNoEndTaskList();
        for (TaskInfoDTO taskInfoDTO : noEndTaskList) {
            TransportTask transportTask = new TransportTask(taskInfoDTO);
            taskMap.put(transportTask.getTaskId(), transportTask);
        }
    }

    @Override
    public void addTask(String taskId) {
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setTaskId(taskId);
        List<TaskInfoDTO> taskInfoDTOS = taskService.detail(taskSearchDTO);
        taskMap.put(taskId, new TransportTask(taskInfoDTOS.get(0)));
    }

    @Override
    public void removeTask(String taskId) {
        taskMap.remove(taskId);
    }

    @Override
    public Long createChargeTask(Amr amr, Station station) {
        // 创建充电任务
        String subTaskId = IdUtil.getSnowflakeNextIdStr();
        Long taskId = IdUtil.getSnowflakeNextId();

        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setActionCommandId(IdUtil.getSnowflakeNextIdStr());
        actionDTO.setActionType(Action.CommandWord.START_CHARGING);
        actionDTO.setSubTaskId(subTaskId);
        List<ActionDTO> actionDTOS = new ArrayList<>();
        actionDTOS.add(actionDTO);

        SubTaskInfoDTO subTaskInfoDTO = genSubTaskInfoDTO(station, subTaskId, taskId);
        subTaskInfoDTO.setActions(actionDTOS);

        TaskInfoDTO taskInfoDTO = genTaskInfoDTO(amr,taskId,subTaskInfoDTO,TransportTask.Type.CHARGE);
        TransportTask transportTask = new TransportTask(taskInfoDTO);
        taskMap.put(transportTask.getTaskId(),transportTask);
        taskService.createInnerTask(taskInfoDTO);
        return taskId;
    }

    @Override
    public Long createParkTask(Amr amr, Station station) {
        // 创建充电任务
        String subTaskId = IdUtil.getSnowflakeNextIdStr();
        Long taskId = IdUtil.getSnowflakeNextId();

        SubTaskInfoDTO subTaskInfoDTO = genSubTaskInfoDTO(station, subTaskId, taskId);

        TaskInfoDTO taskInfoDTO = genTaskInfoDTO(amr, taskId, subTaskInfoDTO,TransportTask.Type.PARK);
        TransportTask transportTask = new TransportTask(taskInfoDTO);
        taskMap.put(transportTask.getTaskId(),transportTask);
        taskService.createInnerTask(taskInfoDTO);
        return taskId;
    }

    @NotNull
    private TaskInfoDTO genTaskInfoDTO(Amr amr, Long taskId, SubTaskInfoDTO subTaskInfoDTO,TransportTask.Type type) {
        TaskInfoDTO taskInfoDTO = new TaskInfoDTO();
        taskInfoDTO.setTaskId(taskId);
        taskInfoDTO.setType(type.getCode());
        taskInfoDTO.setCreateTime(LocalDateTime.now());
        taskInfoDTO.setStatus(TaskStatusEnum.ASSIGNED.getCode());
        taskInfoDTO.setAmrId(amr.getAmrId());
        List<SubTaskInfoDTO> list = new ArrayList<>();
        list.add(subTaskInfoDTO);
        taskInfoDTO.setSubTaskInfoDTOS(list);
        return taskInfoDTO;
    }

    @NotNull
    private SubTaskInfoDTO genSubTaskInfoDTO(Station station, String subTaskId, Long taskId) {
        SubTaskInfoDTO subTaskInfoDTO = new SubTaskInfoDTO();
        subTaskInfoDTO.setSubTaskId(subTaskId);
        subTaskInfoDTO.setTaskId(taskId.toString());
        subTaskInfoDTO.setStationId(Integer.parseInt(station.getGlobalId().getLocalId()));
        String mapName = station.getGlobalId().getMapName();
        Continent continent = mapManager.getContinent(mapName);
        subTaskInfoDTO.setMapId(continent.getMapInfoDTO().getMapId());
        return subTaskInfoDTO;
    }


    @Override
    public void cancelTask(String taskId) {

    }

    @Override
    public void pause(String taskId) {

    }

    @Override
    public void continueTask(String taskId) {

    }

    @Override
    public void suspendTask(String taskId) {
        TransportTask task = queryTask(taskId);
        task.setTaskStatus(TaskStatusEnum.SUSPENDED);
        ApplicationContextUtils.asyncPublishEvent(new TaskSuspendEvent(this,taskId));
    }

    @Override
    public TransportTask queryTask(String taskId) {
        return taskMap.get(taskId);
    }

    @Override
    public List<TransportTask> queryTaskByState(TaskStatusEnum taskStatusEnum) {
        return null;
    }

    @Override
    public List<TransportTask> findFreeDispatchableTask() {
        List<TransportTask> transportTasks = new ArrayList<>();
        for (TransportTask transportTask : taskMap.values()) {
            if (TaskStatusEnum.TO_BE_ASSIGNED.equals(transportTask.getTaskStatus())
                && StringUtils.isEmpty(transportTask.getIntendedAmrId())) {
                transportTasks.add(transportTask);
            }
        }
        return transportTasks;
    }

    @Override
    public List<TransportTask> findReservedDispatchableTask() {
        List<TransportTask> transportTasks = new ArrayList<>();
        for (TransportTask transportTask : taskMap.values()) {
            if (TaskStatusEnum.TO_BE_ASSIGNED.equals(transportTask.getTaskStatus())
                && StringUtils.isNotEmpty(transportTask.getIntendedAmrId())) {
                transportTasks.add(transportTask);
            }
        }
        return transportTasks;
    }
}
