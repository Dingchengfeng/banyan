package com.standard.banyan.common.util;

/**
 * @author dingchengfeng
 */
public class GenBezierUtil {

    public static void main(String[] args) {
        GenBezierUtil bezierUtil = new GenBezierUtil();
        Pose start = new Pose(-2.02, -2.02, 0);
        Pose end = new Pose(0.01, -0.02, 0);
        bezierUtil.getBezierByStartAndEndPose(start, end);
    }

    public GenBezierUtil(){}

    /**
    * 通过起点和终点得到一条3阶贝塞尔曲线
    * @param start 3阶贝塞尔曲线的起点
    * @param end 3阶贝塞尔曲线的终点
    * @return 通过起点和终点得到一条3阶贝塞尔曲线
    */
    public Pose[] getBezierByStartAndEndPose(Pose start, Pose end) {
        Pose[] poses = new Pose[4];
        poses[0] = start;
        poses[3] = end;

        // 长度
        double d = Math.hypot(end.y - start.y, end.x - start.x);

        // 靠近终点的控制点坐标
        poses[2] = new Pose(end.x - 0.5 * d * Math.cos(end.yaw), end.y - 0.5 * d * Math.sin(end.yaw), 0);

        double detD = (end.x - start.x) * Math.cos(end.yaw + 0.5 * Math.PI) + (end.y - start.y) * Math.sin(end.yaw + 0.5 * Math.PI);

        double detTheta = detAngle(start.yaw, end.yaw + 0.5 * Math.PI);

        detD = Math.abs(detD / Math.cos(detTheta));

        if(detD >= 0.5 * d) {
            detD = 0.5 * d;
        }
        poses[1] = new Pose(start.x + detD * Math.cos(start.yaw), start.y + detD * Math.sin(start.yaw), 0);

        System.out.printf("%f, %f\n", poses[0].x, poses[0].y);
        System.out.printf("%f, %f\n", poses[1].x, poses[1].y);
        System.out.printf("%f, %f\n", poses[2].x, poses[2].y);
        System.out.printf("%f, %f\n", poses[3].x, poses[3].y);
        return poses;
    }

    /**
    * 计算点到3阶贝塞尔曲线的最短距离
    * @param p0 3阶贝塞尔曲线的起点
    * @param p1 3阶贝塞尔曲线的第一个控制点
    * @param p2 3阶贝塞尔曲线的第二个控制点
    * @param p3 3阶贝塞尔曲线的终点
    * @param p 参考点
    * @param accuracy 计算精度，推荐值0.03
    * @return 点到3阶贝塞尔曲线的最短距离
    */
    public double getDistanceTo3Bezier(Pose p0, Pose p1, Pose p2, Pose p3, Pose p, double accuracy) {
        double minT = getClosetPointTo3Bezier(p0, p1, p2, p3, p, accuracy);
        Pose pT = get3BezierPoint(p0, p1, p2, p3, minT);
        double minD = Math.hypot(pT.y - p.y, pT.x - p.x);

        return minD;
    }

    /**
    * 计算点到3阶贝塞尔曲线的最近点的时间参数t
    * @param p0 3阶贝塞尔曲线的起点
    * @param p1 3阶贝塞尔曲线的第一个控制点
    * @param p2 3阶贝塞尔曲线的第二个控制点
    * @param p3 3阶贝塞尔曲线的终点
    * @param p 参考点
    * @param accuracy 计算精度，推荐值0.03
    * @return 点到3阶贝塞尔曲线的最近点的时间参数t
    */
    private double getClosetPointTo3Bezier(Pose p0, Pose p1, Pose p2, Pose p3, Pose p, double accuracy) {
        double minD = 9999999;
        double minT = 0.0;
        double d = Math.hypot(p0.y - p3.y, p0.x - p3.x);
        double detT = accuracy / d;
        detT = detT < 0.0005 ? 0.001 : detT;
        detT = detT > 0.1 ? 0.1 : detT;
        for(double t = 0; t < 1.001; t+=detT) {
            Pose pT = get3BezierPoint(p0, p1, p2, p3, t);
            double dT = Math.hypot(p.y - pT.y, p.x - pT.x);
            if(minD > dT) {
                minD = dT;
                minT = t;
            }
        }

        return minT;
    }

    /**
    * 从起点处开始拟合3阶贝塞尔曲线
    * @param start 起点
    * @param p0 3阶贝塞尔曲线的起点
    * @param p1 3阶贝塞尔曲线的第一个控制点
    * @param p2 3阶贝塞尔曲线的第二个控制点
    * @param p3 3阶贝塞尔曲线的终点
    * @return 从起点处开始拟合的3阶贝塞尔曲线
    */
    public Pose[] fitting3BezierForStart(Pose start, Pose p0, Pose p1, Pose p2, Pose p3) {
        Pose[] bezier = new Pose[4];
        bezier[0] = new Pose(0, 0, 0);    //3阶贝塞尔曲线的起点
        bezier[1] = new Pose(0, 0, 0);    //3阶贝塞尔曲线的第一个控制点
        bezier[2] = new Pose(0, 0, 0);    //3阶贝塞尔曲线的第二个控制点
        bezier[3] = new Pose(0, 0, 0);    //3阶贝塞尔曲线的终点

        double start_t = getClosetPointTo3Bezier(p0 ,p1, p2, p3, start, 0.01);
        bezier[0] = start;
        bezier[1] = get2BezierPoint(p1, p2, p3, start_t);
        bezier[2] = get1BezierPoint(p2, p3, start_t);
        bezier[3] = p3;
        
        return bezier;
    }

    /**
    * 从终点处开始拟合3阶贝塞尔曲线
    * @param end 终点
    * @param p0 3阶贝塞尔曲线的起点
    * @param p1 3阶贝塞尔曲线的第一个控制点
    * @param p2 3阶贝塞尔曲线的第二个控制点
    * @param p3 3阶贝塞尔曲线的终点
    * @return 从终点处开始拟合的3阶贝塞尔曲线
    */
    public Pose[] fitting3BezierForEnd(Pose end, Pose p0, Pose p1, Pose p2, Pose p3) {
        Pose[] bezier = new Pose[4];
        bezier[0] = new Pose(0, 0, 0);    //3阶贝塞尔曲线的起点
        bezier[1] = new Pose(0, 0, 0);    //3阶贝塞尔曲线的第一个控制点
        bezier[2] = new Pose(0, 0, 0);    //3阶贝塞尔曲线的第二个控制点
        bezier[3] = new Pose(0, 0, 0);    //3阶贝塞尔曲线的终点

        double start_t = getClosetPointTo3Bezier(p0 ,p1, p2, p3, end, 0.01);
        bezier[0] = p0;
        bezier[1] = get1BezierPoint(p0, p1, start_t);
        bezier[2] = get2BezierPoint(p0, p1, p2, start_t);
        bezier[3] = end;
        
        return bezier;
    }

    private Pose get1BezierPoint(Pose p0, Pose p1, double t) {
        if(t < 0 || t > 1) {
            System.out.printf("1贝塞尔曲线的时间参数设置错误，t = %f", t);
        }

        Pose point = new Pose(0, 0, 0);
        double l_t = 1.0 - t;
        point.x = l_t * p0.x + t * p1.x;
        point.y = l_t * p0.y + t * p1.y;

        return point;
    }

    private Pose get2BezierPoint(Pose p0, Pose p1, Pose p2, double t) {
        if(t < 0 || t > 1) {
            System.out.printf("2贝塞尔曲线的时间参数设置错误，t = %f", t);
        }

        Pose point = new Pose(0, 0, 0);
        double l_t = 1.0 - t;
        point.x = l_t * l_t * p0.x + 2 * l_t * t * p1.x + t * t * p2.x;
        point.y = l_t * l_t * p0.y + 2 * l_t * t * p1.y + t * t * p2.y;

        return point;
    }

    private Pose get3BezierPoint(Pose p0, Pose p1, Pose p2, Pose p3, double t) {
        if(t < 0 || t > 1) {
            System.out.printf("3贝塞尔曲线的时间参数设置错误，t = %f", t);
        }

        Pose point = new Pose(0, 0, 0);
        double l_t = 1.0 - t;
        point.x = l_t * l_t * l_t * p0.x + 3 * l_t * l_t * t * p1.x + 3 * l_t * t * t * p2.x + t * t * t * p3.x;
        point.y = l_t * l_t * l_t * p0.y + 3 * l_t * l_t * t * p1.y + 3 * l_t * t * t * p2.y + t * t * t * p3.y;

        return point;
    }

    public double detAngle(double theta1, double theta0) {
        double det_theta = theta1 - theta0;
        if(Math.abs(det_theta) > 9999999) {
            System.out.printf("角度错误");
            return det_theta;
        }

        while(det_theta > Math.PI) {
            det_theta = det_theta - 2 * Math.PI;
        }

        while(det_theta < -Math.PI) {
            det_theta = det_theta + 2 * Math.PI;
        }

        return det_theta;
    }

    public static class Pose {
        public double x;
        public double y;
        public double yaw;

        public Pose(double x, double y, double yaw) {
            this.x = x;
            this.y = y;
            this.yaw = yaw;
        }
    }

}

