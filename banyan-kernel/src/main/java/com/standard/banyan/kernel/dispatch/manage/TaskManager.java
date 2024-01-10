package com.standard.banyan.kernel.dispatch.manage;

import com.tiansu.nts.rcs.enums.state.TaskStatusEnum;
import com.tiansu.nts.rcs.imap.manage.resource.pose.Station;
import com.tiansu.nts.rcs.kernel.dispatch.manage.domain.TransportTask;
import com.tiansu.nts.rcs.kernel.execute.manage.Amr;

import java.util.List;

/**
 * 任务管理器
 *
 * @author dingchengfeng
 * @description 任务管理器
 * @date 2023/07/12
 */
public interface TaskManager {
    /**
     * 初始化，加载不处于终态的任务
     */
    void init();

    /**
     * 新增任务
     * @param taskId 任务id
     */
    void addTask(String taskId);

    /**
     * 移除任务
     * @param taskId 任务id
     */
    void removeTask(String taskId);

    /**
     * 创建充电任务
     * @param amr 机器人
     * @param station 站点
     * @return taskId
     */
    Long createChargeTask(Amr amr, Station station);

    /**
     * 创建停靠任务
     * @param amr 机器人
     * @param station 站点
     * @return taskId
     */
    Long createParkTask(Amr amr, Station station);

    /**
     * 取消任务
     * @param taskId 任务id
     */
    void cancelTask(String taskId);

    /**
     * 暂停任务
     * @param taskId 任务id
     */
    void pause(String taskId);

    /**
     * 继续任务
     * @param taskId 任务id
     */
    void continueTask(String taskId);

    /**
     * 挂起任务
     * @param taskId 任务id
     */
    void suspendTask(String taskId);
    /**
     * 查询任务
     *
     * @param taskId 任务id
     * @return 任务
     */
    TransportTask queryTask(String taskId);

    /**
     * 查询指定状态的任务
     * @param taskStatusEnum 状态
     * @return 任务集合
     */
    List<TransportTask> queryTaskByState(TaskStatusEnum taskStatusEnum);

    /**
     * 查询可自由分配的任务
     * @return 任务集合
     */
    List<TransportTask> findFreeDispatchableTask();

    /**
     * 查询预约的任务
     * @return
     */
    List<TransportTask> findReservedDispatchableTask();
}
