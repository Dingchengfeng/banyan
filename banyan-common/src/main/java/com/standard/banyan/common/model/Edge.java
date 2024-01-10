package com.standard.banyan.common.model;


import com.standard.banyan.common.math.Point2D;

import java.util.List;

/**
 * 边
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public interface Edge extends Resource {
    /**
     * 边的代价
     *
     * @return Float 代价
     * @since 3.1.0
     */
    Double cost();

    /**
     * 起点
     *
     * @return Node 节点
     */
    Node getStartNode();

    /**
     * 获取起点id
     *
     * @return 起点id
     */
    GlobalId getStartNodeId();

    /**
     * 终点
     *
     * @return Node 节点
     */
    Node getEndNode();

    /**
     * 获取终点id
     *
     * @return 终点id
     */
    GlobalId getEndNodeId();

    /**
     * 起点执行角度
     *
     * @return Double 角度
     */
    Double getStartExecuteTheta();

    /**
     * 终点执行角度
     *
     * @return Double 角度
     */
    Double getEndExecuteTheta();

    /**
     * 边的类型
     *
     * @return Type 类型
     */
    Type getEdgeType();

    /**
     * 获取控制点
     * @return 控制点集合
     */
    List<Point2D<Double>> getControlPoints();

    /**
     * 车头方向
     *
     * @return 车头方向
     */
    RobotDirection getRobotDirection();

    /**
     * 边的长度
     * @return 长度
     */
    Double getLength();

    /**
     * 边的曲线类型
     */
    enum Type {
        /**
         * 直线
         */
        LINE,
        /**
         * 贝塞尔
         */
        BEZIER
    }

    /**
     * 车头朝向（相对边的方向）,FORWARD 前,BACKWARD 后,LEFT 左,RIGHT 右
     */
    enum RobotDirection {
        /**
         * 前
         */
        FORWARD(0D),
        /**
         * 后
         */
        BACKWARD(Math.PI),
        /**
         * 左
         */
        LEFT(Math.PI / 2),
        /**
         * 右
         */
        RIGHT(-Math.PI / 2);

        /**
         * 边的方向旋转到车头方向角度
         */
        private final Double includedAngle;

        RobotDirection(Double includedAngle) {
            this.includedAngle = includedAngle;
        }

        public Double getIncludedAngle() {
            return includedAngle;
        }
    }

}
