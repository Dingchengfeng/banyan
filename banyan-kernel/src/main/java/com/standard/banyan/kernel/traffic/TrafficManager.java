package com.standard.banyan.kernel.traffic;

import com.tiansu.nts.rcs.model.Resource;
import com.tiansu.nts.rcs.model.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 交通管理器
 *
 * @author dingchengfeng
 * @description 封装交管模块对外接口
 * @date 2023/07/07
 */
public interface TrafficManager {

    /**
     * 启动交通管理器
     *
     * @since 3.1.0
     */
    void start();

    /**
     * 停止交通管理器
     */
    void stop();

    /**
     * 申请交管
     *
     * @param steps 运动步骤
     * @param amrInfo 机器人信息
     * @since 3.1.0
     */
    void applyTrafficForSteps(List<Step> steps,AmrInfo amrInfo);

    /**
     * 申请资源
     *
     * @param resourceIds 资源id集合
     * @param amrId 机器人id
     * @since 3.1.0
     */
    void releaseTraffic(List<Resource.GlobalId> resourceIds,String amrId);

    /**
     * 释放资源
     *
     * @param amrId 机器人id
     * @since 3.1.0
     */
    void releaseAmrTraffic(String amrId);

    /**
     * 取消申请
     *
     * @param amrId 机器人id
     * @since 3.1.0
     */
    void cancelApply(String amrId);

    /**
     * amr交管相关的信息
     */
    @Getter
    @AllArgsConstructor
    class AmrInfo {
        /**
         * 唯一标识
         */
        private String amrId;

        /**
         * 整体尺寸：长
         */
        private Double wholeLength;

        /**
         * 整体尺寸：宽
         */
        private Double wholeWidth;

        /**
         * 底盘尺寸：长
         */
        private Double amrLength;

        /**
         * 底盘尺寸：宽
         */
        private Double amrWidth;

        /**
         * 车的几何中心相对于运动中心的偏移量，车头方向为正方向
         */
        private Double centerOffset;
    }
}
