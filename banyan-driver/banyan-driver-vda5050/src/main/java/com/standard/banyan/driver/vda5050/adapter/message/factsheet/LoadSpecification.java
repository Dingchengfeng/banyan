package com.standard.banyan.driver.vda5050.adapter.message.factsheet;

import lombok.Data;

import java.util.List;

/**
 * @author dengxx
 * @description 负载能力的抽象规范
 * @date 2023/08/22
 */
@Data
public class LoadSpecification {
    /**
     * 负载位置/负载处理设备列表
     */
    private List<String> loadPositions;
    /**
     * AGV 可以处理的负载集列表
     */
    private List<LoadSet> loadSets;
    @Data
    private static class LoadSet {
        /**
         * 负载工具名称
         */
        private String setName;
        /**
         * 负载类型
         */
        private String loadType;
        /**
         * 载具的的位置信息
         */
        private List<String> loadPositions;
        /**
         * 状态消息中参数loads[]中定义的边界框引用
         */
        private BoundingBoxReference boundingBoxReference;
        /**
         * 负载尺寸
         */
        private LoadDimension loadDimensions;
        /**
         * 负载类型最大重量
         */
        private Double maxWeight;
        /**
         * 该负载类型下的最小允许高度
         */
        private Double minLoadHandlingHeight;
        /**
         * 该负载类型下的最大允许高度
         */
        private Double maxLoadHandlingHeight;
        /**
         * 该负载类型下的最小允许深度
         */
        private Double minLoadHandlingDepth;
        /**
         * 该负载类型下的最大允许深度
         */
        private Double maxLoadHandlingDepth;
        /**
         * 允许的最小倾斜弧度
         */
        private Double minLoadHandlingTilt;
        /**
         * 允许的最大倾斜弧度
         */
        private Double maxLoadHandlingTilt;
        /**
         * 负载时允许的最大速度
         */
        private Double agvSpeedLimit;
        /**
         * 负载类型下允许的最大加速度
         */
        private Double agvAccelerationLimit;
        /**
         * 负载类型下允许的最大减速度
         */
        private Double agvDecelerationLimit;
        /**
         * 装货物的大概时间,单位s
         */
        private Double pickTime;
        /**
         * 放下负载的大概时间，单位s
         */
        private Double dropTime;
        /**
         * 负载处理系统的文本描述
         */
        private String description;

        @Data
        public static class BoundingBoxReference {
            /**
             * 参考点的 x 坐标
             */
            private Double x;
            /**
             * 参考点的 y 坐标
             */
            private Double y;
            /**
             * 参考点的 z 坐标
             */
            private Double z;
            /**
             * 载荷边界框的方向。 对于拖船列车等很重要
             */
            private Double theta;
        }
        @Data
        public static class LoadDimension {
            /**
             * 负载边界框的绝对长度
             */
            private Double length;
            /**
             * 负载边界框的绝对宽度
             */
            private Double width;
            /**
             * 负载边界框的绝对高度
             */
            private Double height;
        }

    }
}
