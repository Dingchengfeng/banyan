package com.standard.banyan.kernel.route;


import com.standard.banyan.common.model.Step;

import java.util.List;

/**
 * 路由
 *
 * @author dingchengfeng
 * @description 路由规划
 * @date 2023/07/07
 */
public interface Router {
    /**
     * 初始化
     *
     * @since 3.1.0
     */
    void init();

    /**
     * 刷新路由
     */
    void refresh();

    /**
     * 生成完整路由
     *
     * @param amrId 机器人id
     * @param startPose 起点位姿
     * @param endPose 终点位姿
     * @return 完整路由
     * @since 3.1.0
     */
    Route genRoute(String amrId, Pose startPose,Pose endPose);

    /**
     * 计算代价
     * @param amrId 机器人id
     * @param startPose 起点位姿
     * @param endPose 终点位姿
     * @return 运动代价; null表示不可达
     * @since 3.1.0
     */
    Double calculateCost(String amrId, Pose startPose,Pose endPose);

    /**
     * 查找最近的终点
     * @param amrId 机器人id
     * @param startPose 起点位姿
     * @param stations 终点站
     * @return
     */
    Station findNearestEndStation(String amrId,Pose startPose, Set<Station> stations);

    /**
     * 找最好的回归点
     * @param amrId 机器人id
     * @param pose 当前位姿
     * @return 回归点
     * @since 3.1.0
     */
    Node findBestReturnNode(String amrId,Pose pose);

    /**
     * 生成回归步骤
     *
     * @param amrId 机器人id
     * @param startPose 起点位姿
     * @param endPose 终点位姿
     * @return List<Step> 回归步骤
     * @since 3.1.0
     */
    List<Step> genReturnSteps(String amrId, Pose startPose,Pose endPose);
}
