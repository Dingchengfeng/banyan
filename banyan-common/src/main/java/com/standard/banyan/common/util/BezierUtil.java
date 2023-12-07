package com.standard.banyan.common.util;

import com.standard.banyan.common.math.Point2D;

import java.util.List;

/**
 * @author dingchengfeng
 * @date 2023/12/05
 */
public class BezierUtil {

    /**
     * 拟合贝塞尔的线段数
     */
    private static final int LINE_FIT_NUM = 10;

    /**
     * 计算用一些线段拟合贝塞尔的长度
     *
     * @param points 曲线描述点
     * @return 拟合后的长度
     */
    public static float getCurveLength(List<Point2D<Double>> points) {
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
    public static Double getCurvePointX(List<Point2D<Double>> points, double t) {
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
    public static Double getCurvePointY(List<Point2D<Double>> points, double t) {
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
    public static Double getCurvePointDx(List<Point2D<Double>> points, double t) {
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
    public static Double getCurvePointDy(List<Point2D<Double>> points, double t) {
        return 3 * (1 - t) * (1 - t) * (points.get(1).getY() - points.get(0).getY()) + 6 * (1 - t) * t * (
            points.get(2).getY() - points.get(1).getY()) + 3 * t * t * (points.get(3).getY() - points.get(2).getY());
    }

    /**
     * 切线角度
     * @param points 曲线描述点
     * @param t 参数t
     * @return 参数t处曲线的切线角度
     */
    public static Double getTheta(List<Point2D<Double>> points, double t) {
        Double dx = getCurvePointDx(points, t);
        Double dy = getCurvePointDy(points, t);
        return Math.atan2(dy, dx);
    }

}
