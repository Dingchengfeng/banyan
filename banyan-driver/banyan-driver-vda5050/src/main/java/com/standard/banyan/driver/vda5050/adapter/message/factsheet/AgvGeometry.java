package com.standard.banyan.driver.vda5050.adapter.message.factsheet;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * AGV几何结构的详细定义
 * @author dingchengfeng
 * @date 2023/08/22
 */
@Getter
@Setter
public class AgvGeometry {

    /**
     * 车轮列表，包含车轮布置和几何形状
     */
    private List<WheelDefinition> wheelDefinitions;

    /**
     * 2D 中 AGV 包络曲线列表
     */
    private List<Envelope2d> envelopes2d;
    /**
     * 3D 中 AGV 包络曲线列表
     */
    private List<Envelope3d> envelopes3d;
    @Getter
    @Setter
    public static class WheelDefinition {

        private Type type;
        /**
         * 车轮是否被主动驱动
         */
        private Boolean isActiveDriven;
        /**
         * 车轮是否主动转向
         */
        private Boolean isActiveSteered;
        /**
         * 车辆位置信息
         */
        private Position position;
        /**
         *  车轮的直径，单位m
         */
        private Double diameter;
        /**
         * 车轮的宽度，单位m
         */
        private Double width;
        /**
         * 轮子中心到旋转点的标称位移（脚轮必需）。 如果该参数没有定义，则假定为0
         */
        private Double centerDisplacement;
        /**
         * 制造商可以使用它来定义约束
         */
        private String constraints;

        @Getter
        @Setter
        public static class Position {
            /**
             * AGV 坐标系中的 x 位置
             */
            private Double x;
            /**
             * AGV 坐标系中的 y 位置
             */
            private Double y;
            /**
             * AGV坐标系中车轮的方向
             */
            private Double theta;
        }

        public enum Type {
            DRIVE,
            CASTER,
            FIXED,
            /**
             * 麦克纳姆轮
             */
            MECANUM
        }
    }
    @Getter
    @Setter
    public static class Envelope2d {
        /**
         * 包络线集的名称
         */
        private String set;
        /**
         * 作为 x/y 多边形的包络曲线被假定为封闭的并且必须是非自相交的
         */
        private List<PolygonPoint> polygonPoints;
        /**
         * 描述
         */
        private String description;
        @Getter
        @Setter
        public static class PolygonPoint {
            /**
             * 多边形点的 x 位置
             */
            private Double x;
            /**
             * 多边形点的 y 位置
             */
            private Double y;

        }
    }
    @Getter
    @Setter
    public static class Envelope3d {
        /**
         * 包络线集的名称
         */
        private String set;
        /**
         * 数据格式,如DXF
         */
        private String format;
        /**
         * 3D 包络曲线数据，格式在‚format 中指定
         */
        private Object data;
        /**
         * 用于下载 3D 包络曲线数据的协议和 url 定义
         */
        private String url;
        /**
         * 预留字段
         */
        private String description;
    }
}
