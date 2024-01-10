package com.standard.banyan.kernel.traffic.shape;

import com.tiansu.nts.rcs.math.Point2D;
import com.tiansu.nts.rcs.math.Vector;
import com.tiansu.nts.rcs.model.Resource;
import lombok.Getter;
import lombok.ToString;

/**
 * @author dingchengfeng
 * @description 矩形
 * @date 2023/07/10
 */
@Getter
@ToString
public class Rectangle implements Shape {
    /**
     * 矩形角度
     */
    private final Double theta;

    /**
     * 长：平行于车头方向的是长
     */
    private final Double length;

    /**
     * 宽
     */
    private final Double width;

    /**
     * 中心点
     */
    private final Point2D<Double> center;

    /**
     * 左前点
     */
    private Point2D<Double> leftFront;

    /**
     * 左后点
     */
    private Point2D<Double> leftBack;

    /**
     * 右后点
     */
    private Point2D<Double> rightBack;

    /**
     * 右前店
     */
    private Point2D<Double> rightFront;

    /**
     * 图形对应的资源id
     */
    private final Resource.GlobalId resourceId;

    /**
     * 对角线长
     */
    private final Double diagonal;


    public Rectangle(Double theta, Double length, Double width, Point2D<Double> center, Resource.GlobalId resourceId) {
        this.center = center;
        this.theta = theta;
        this.length = length;
        this.width = width;
        this.resourceId = resourceId;
        this.diagonal = Math.hypot(this.length, this.width);

        initVertex();
    }

    /**
     * 计算增加增量后的矩形中心点
     * @param center 原中心点
     * @param frontIncrease 前向增量，负数表示后向增量
     * @param theta 角度
     * @return 计算增量后的中心点
     */
    public static Point2D<Double> calculateCenterAfterFrontIncrease(Point2D<Double> center,Double frontIncrease,Double theta){
        Double x = center.getX() + frontIncrease * Math.cos(theta) / 2;
        Double y = center.getY() + frontIncrease * Math.sin(theta) / 2;
        return new Point2D<>(x, y);
    }

    /**
     * 计算偏移之后的中心点
     * @param refPoint 参考点
     * @param centerOffset 偏移量
     * @param theta 角度
     * @return 偏移之后的中心点
     */
    public static Point2D<Double> calculateCenterAfterOffset(Point2D<Double> refPoint,Double centerOffset,Double theta){
        Double x = refPoint.getX() + centerOffset * Math.cos(theta);
        Double y = refPoint.getY() + centerOffset * Math.sin(theta);
        return new Point2D<>(x, y);
    }



    private void initVertex() {
        //theta==0的矩形坐标,旋转到真实角度
        leftFront = rotate(new Point2D<>(center.getX() + length / 2, center.getY() + width / 2), theta);
        leftBack = rotate(new Point2D<>(center.getX() - length / 2, center.getY() + width / 2), theta);
        rightBack = rotate(new Point2D<>(center.getX() - length / 2, center.getY() - width / 2), theta);
        rightFront = rotate(new Point2D<>(center.getX() + length / 2, center.getY() - width / 2), theta);
    }

    private Point2D<Double> rotate(Point2D<Double> point, Double theta) {
        Double x = (point.getX() - center.getX()) * Math.cos(theta) - (point.getY() - center.getY()) * Math.sin(theta)
            + center.getX();
        Double y = (point.getY() - center.getY()) * Math.cos(theta) + (point.getX() - center.getX()) * Math.sin(theta)
            + center.getY();
        return new Point2D<>(x, y);
    }

    /**
     * 检测this矩形和入参矩形是否碰撞
     *
     * @param other 另一个矩形
     * @return true 碰撞；false 不碰撞
     */
    @Override
    public boolean collision(Shape other) {
        if(!(other instanceof Rectangle)){
            return other.collision(this);
        }
        Rectangle that = (Rectangle) other;
        //当两个矩形的中心距离大于对角线之和的一半时，不会发生碰撞情况
        Double centerDistance = this.center.distanceTo(that.center);
        if (centerDistance > (this.diagonal + that.diagonal) / 2) {
            return false;
        }
        //当两个矩形的中心距离小于两矩形的最小长或宽时，一定存在碰撞
        if (centerDistance <= Math.min(Math.min(this.length, this.width), Math.min(that.length, that.width))) {
            return true;
        }

        //this左边向量
        Vector<Double> thisLeftVector = new Vector<>((this.getLeftFront().getX() - this.getLeftBack().getX()),
            (this.getLeftFront().getY() - this.getLeftBack().getY()));
        //this前边向量
        Vector<Double> thisFrontVector = new Vector<>((this.getLeftFront().getX() - this.getRightFront().getX()),
            (this.getLeftFront().getY() - this.getRightFront().getY()));
        //that左边向量
        Vector<Double> thatLeftVector = new Vector<>((that.getLeftFront().getX() - that.getLeftBack().getX()),
            (that.getLeftFront().getY() - that.getLeftBack().getY()));
        //that前边向量
        Vector<Double> thatFrontVector = new Vector<>((that.getLeftFront().getX() - that.getRightFront().getX()),
            (that.getLeftFront().getY() - that.getRightFront().getY()));
        //两中心点的向量
        Vector<Double> centerVector = new Vector<>((that.getCenter().getX() - this.getCenter().getX()),
            (that.getCenter().getY() - this.getCenter().getY()));

        //只要有一个方向分离，就不碰撞
        if (!checkCollision(thisLeftVector, thatLeftVector, thatFrontVector, centerVector)) {
            return false;
        }
        if (!checkCollision(thisFrontVector, thatLeftVector, thatFrontVector, centerVector)) {
            return false;
        }
        if (!checkCollision(thatLeftVector, thisLeftVector, thisFrontVector, centerVector)) {
            return false;
        }
        return checkCollision(thatFrontVector, thisLeftVector, thisFrontVector, centerVector);
    }

    /**
     * 检测以baseVector为投影基础向量的情况下，是否满足碰撞条件
     *
     * @param baseVector       投影的基础向量,其他向量都往这个向量上投影
     * @param otherLeftVector  另一个矩形上被投影的向量
     * @param otherFrontVector 另一个矩形上被投影的相邻向量
     * @param centerVector     两中心点向量
     * @return true 碰撞 false 不碰撞
     */
    private boolean checkCollision(Vector<Double> baseVector, Vector<Double> otherLeftVector,
        Vector<Double> otherFrontVector, Vector<Double> centerVector) {
        return centerVector.projectionTo(baseVector) <=
            (otherLeftVector.projectionTo(baseVector) + otherFrontVector.projectionTo(baseVector) + baseVector.modulo()) / 2;
    }

}
