package com.standard.banyan.kernel.traffic.shape;

import com.tiansu.nts.rcs.math.Point2D;
import com.tiansu.nts.rcs.model.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingchengfeng
 * @description 计算贝塞尔运动的包络图形
 * @date 2023/08/04
 */
public class BezierStep2Rectangle {
    private BezierStep2Rectangle() {
    }

    /**
     * 矩形框数量调整系数,越大矩形框越多（由于取整，不同参数会有矩形数量相等的情况），推荐值范围1.5~2
     */
    private static final float LAMBDA = 4f;

    /**
     * 拟合贝塞尔的线段数
     */
    private static final int LINE_FIT_NUM = 10;

    /**
     * 最小矩形数
     */
    private static final int MIN_RECTANGLE_NUM = 3;

    /**
     * 用一系列矩形拟合贝塞尔
     * 当前只能应用于3阶贝塞尔曲线（端点+2个控制点+终点，共4个点的贝塞尔曲线）
     *
     * @param points     端点和控制点，依次为：起点、控制点1、控制点2、终点
     * @param length     矩形的长
     * @param width      矩形的宽
     * @param resourceId 资源id
     * @return List<Rectangle> 一系列拟合矩形
     */
    public static List<Shape> calculateRectangle(List<Point2D<Double>> points, Double length, Double width,
        Resource.GlobalId resourceId, Double frontIncrease, Double centerOffset) {
        int rectangleNumber = getCurveRectangleNumber(points, length);

        List<Shape> result = new ArrayList<>();
        for (int i = 0; i < rectangleNumber; i++) {
            float t = 1.0f / (rectangleNumber - 1) * i;
            Double x = getCurvePointX(points, t);
            Double y = getCurvePointY(points, t);
            Double dx = getCurvePointDx(points, t);
            Double dy = getCurvePointDy(points, t);
            Double theta = Math.atan2(dy, dx);
            Point2D<Double> center = Rectangle.calculateCenterAfterOffset(new Point2D<>(x, y), centerOffset, theta);
            if (i < rectangleNumber - 1) {
                result.add(new Rectangle(theta, length, width, center, resourceId));
            } else {
                // 最后一个矩形
                result.add(new Rectangle(theta, length + frontIncrease, width,
                    Rectangle.calculateCenterAfterFrontIncrease(center, frontIncrease, theta), resourceId));
            }

        }

        return result;
    }

    /**
     * 根据曲线的近似长度确定矩形的数量
     *
     * @param points 描述点
     * @param length 矩形的长
     * @return 矩形数量
     */
    private static int getCurveRectangleNumber(List<Point2D<Double>> points, Double length) {
        float curveLength = getCurveLength(points);

        int num = (int)(LAMBDA * curveLength / length);

        //保证首中尾最少3个位置有矩形
        return Math.max(num, MIN_RECTANGLE_NUM);
    }

    /**
     * 计算用一些线段拟合贝塞尔的长度
     *
     * @param points 曲线描述点
     * @return 拟合后的长度
     */
    private static float getCurveLength(List<Point2D<Double>> points) {
        //将贝塞尔曲线离散成 LINE_FIT_NUM 段线段，计算贝塞尔曲线的长度
        float curveLength = 0f;
        for (int i = 0; i < LINE_FIT_NUM; i++) {
            float t0 = 1f / LINE_FIT_NUM * i;
            Double x = getCurvePointX(points, t0);
            Double y = getCurvePointY(points, t0);
            Point2D<Double> p0 = new Point2D<>(x, y);

            float t1 = 1f / LINE_FIT_NUM * (i + 1);
            x = getCurvePointX(points, t1);
            y = getCurvePointY(points, t1);
            Point2D<Double> p1 = new Point2D<>(x, y);

            curveLength += Math.hypot(p1.getX() - p0.getX(), p1.getY() - p0.getY());
        }
        return curveLength;
    }

    /**
     * 根据参数t获取曲线上该点x坐标值
     *
     * @param points 曲线描述点
     * @param t      参数t
     * @return x坐标
     */
    private static Double getCurvePointX(List<Point2D<Double>> points, float t) {
        return (1 - t) * (1 - t) * (1 - t) * points.get(0).getX() + 3 * (1 - t) * (1 - t) * t * points.get(1).getX()
            + 3 * (1 - t) * t * t * points.get(2).getX() + t * t * t * points.get(3).getX();
    }

    /**
     * 根据参数t获取曲线上该点y坐标值
     *
     * @param points 曲线描述点
     * @param t 参数t
     * @return y 坐标
     */
    private static Double getCurvePointY(List<Point2D<Double>> points, float t) {
        return (1 - t) * (1 - t) * (1 - t) * points.get(0).getY() + 3 * (1 - t) * (1 - t) * t * points.get(1).getY()
            + 3 * (1 - t) * t * t * points.get(2).getY() + t * t * t * points.get(3).getY();
    }

    /**
     * 根据参数t获取曲线上该点x方向对参数t的偏导数
     *
     * @param points 曲线描述点
     * @param t      参数t
     * @return x方向对参数t的偏导数
     */
    private static Double getCurvePointDx(List<Point2D<Double>> points, float t) {
        return 3 * (1 - t) * (1 - t) * (points.get(1).getX() - points.get(0).getX()) + 6 * (1 - t) * t * (
            points.get(2).getX() - points.get(1).getX()) + 3 * t * t * (points.get(3).getX() - points.get(2).getX());
    }

    /**
     * 根据参数t获取曲线上该点y方向对参数t的偏导数
     *
     * @param points 曲线描述点
     * @param t 参数t
     * @return y方向对参数t的偏导数
     */
    private static Double getCurvePointDy(List<Point2D<Double>> points, float t) {
        return 3 * (1 - t) * (1 - t) * (points.get(1).getY() - points.get(0).getY()) + 6 * (1 - t) * t * (
            points.get(2).getY() - points.get(1).getY()) + 3 * t * t * (points.get(3).getY() - points.get(2).getY());
    }
}
