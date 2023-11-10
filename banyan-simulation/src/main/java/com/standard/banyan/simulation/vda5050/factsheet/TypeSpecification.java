package com.standard.banyan.simulation.vda5050.factsheet;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingchengfeng
 * @description 型号规格
 * @date 2023/08/22
 */
@Getter
@Setter
public class TypeSpecification {
    /**
     * 系列名称
     */
    private String seriesName;
    /**
     * 该型号系列机器人的描述信息
     */
    private String seriesDescription;
    /**
     * AGV运动学类型的简化描述
     */
    private AgvKinematic agvKinematic;
    /**
     * 车辆类型
     */
    private AgvClass agvClass;
    /**
     * 最大载重
     */
    private Double maxLoadMass;
    /**
     * 定位类型
     */
    private List<LocalizationType> localizationTypes;
    /**
     * 车辆支持的导航类型
     */
    private List<NavigationType> navigationTypes;


    public enum LocalizationType {
        /**
         * 自然地标
         */
        NATURAL,
        /**
         * 激光反射器
         */
        REFLECTOR,
        /**
         * RFID标签
         */
        RFID,
        /**
         * 二维码
         */
        DMC,
        /**
         * 磁钉导航
         */
        SPOT,
        /**
         * 磁栅导航
         */
        GRID
    }

    public enum NavigationType {
        /**
         * 无需路径规划，AGV遵循物理安装路径
         */
        PHYSICAL_LINDE_GUIDED,
        /**
         * 虚拟路径路径
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
         * 辊筒车
         */
        CONVEYOR,
        /**
         * 牵引车
         */
        TUGGER,
        /**
         * 装载车；带或不带起重装置
         */
        CARRIER
    }
}
