package com.standard.banyan.driver.vda5050.adapter.message.factsheet;

import lombok.Data;

import java.util.List;

/**
 * @author dengxx
 * @description 型号规格
 * @date 2023/08/22
 */
@Data
public class TypeSpecification {
    /**
     * 型号序列号名
     */
    private String seriesName;
    /**
     * AGV运动学类型的简化描述
     */
    private AgvKinematic agvKinematic;
    /**
     * 车辆类型
     */
    private AgvClass agvClass;
    /**
     * Node节点唯一标识
     */
    private Double maxLoadMass;
    /**
     * 本地化类型的简化描述
     */
    private List<LocalizationType> localizationTypes;
    /**
     * 车辆支持的导航类型
     */
    private List<NavigationType> navigationTypes;
    /**
     * 该型号系列机器人的描述信息
     */
    private String seriesDescription;

    public enum LocalizationType {
        NATURAL,
        REFLECTOR,
        RFID,
        DMC,
        SPOT,
        GRID
    }

    public enum NavigationType {
        /**
         * 无需路径规划，AGV遵循物理安装路径
         */
        PHYSICAL_LINDE_GUIDED,
        /**
         * AGV进入固定（虚拟）路径
         */
        VIRTUAL_LINE_GUIDED,
        /**
         * 自主规划其路径
         */
        AUTONOMOUS
    }

    public enum AgvKinematic {
        /**
         * 差速器驱动
         */
        DIFF,
        /**
         * 全向车辆
         */
        OMNI,
        /**
         * 三轮驱动车辆或具有类似运动学的车辆
         */
        THREEWHEEL
    }

    public enum AgvClass {
        /**
         * 叉车
         */
        FORKLIFT,
        /**
         * 输送车
         */
        CONVEYOR,
        /**
         * 拖车
         */
        TUGGER,
        /**
         * 运输车，带或不带提升装置的载运车辆
         */
        CARRIER
    }
}
