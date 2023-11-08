package com.standard.banyan.web.algo;

import Jama.Matrix;

public class CoordinateTransformation {
    private static Matrix T;

    public static void init(Point2D A1,Point2D A2,Point2D B1,Point2D B2){
        // 计算平移向量
        double dAx = A2.x - A1.x;
        double dAy = A2.y - A1.y;
        double dBx = B2.x - B1.x;
        double dBy = B2.y - B1.y;

        // 计算缩放因子
        double sA = Math.sqrt(dAx * dAx + dAy * dAy) / Math.sqrt(dBx * dBx + dBy * dBy);
        double sB = Math.sqrt(dBx * dBx + dBy * dBy) / Math.sqrt(dAx * dAx + dAy * dAy);

        // 计算旋转角度
        double thetaA = Math.atan2(dAy, dAx);
        double thetaB = Math.atan2(dBy, dBx);

        // 构建转换矩阵
        double[][] translation1 = {{1, 0, -A1.x}, {0, 1, -A1.y}, {0, 0, 1}};
        double[][] rotation1 = {{Math.cos(-thetaA), -Math.sin(-thetaA), 0}, {Math.sin(-thetaA), Math.cos(-thetaA), 0}, {0, 0, 1}};
        double[][] scaling = {{sB, 0, 0}, {0, sB, 0}, {0, 0, 1}};
        double[][] rotation2 = {{Math.cos(thetaB), -Math.sin(thetaB), 0}, {Math.sin(thetaB), Math.cos(thetaB), 0}, {0, 0, 1}};
        double[][] translation2 = {{1, 0, B1.x}, {0, 1, B1.y}, {0, 0, 1}};

        T = new Matrix(translation2)
                .times(new Matrix(rotation2))
                .times(new Matrix(scaling))
                .times(new Matrix(rotation1))
                .times(new Matrix(translation1));
    }

    public static Point2D transform(Point2D A){
        // 坐标系B中的点
        double ax = A.x;
        double ay = A.y;

        // 构建齐次坐标向量
        double[][] pointA = {{ax}, {ay}, {1}};

        // 进行坐标转换
        Matrix pointB = T.times(new Matrix(pointA));

        // 提取转换后的坐标
        double bx = pointB.get(0, 0);
        double by = pointB.get(1, 0);

        // 转换后的坐标
        return new Point2D(bx,by);
    }

    public static Point2D inverseTransform(Point2D b){
        // 计算转换矩阵的逆矩阵
        Matrix TInverse = T.inverse();

        // 坐标系B中的点
        double bx = b.x;
        double by = b.y;

        // 构建齐次坐标向量
        double[][] pointB = {{bx}, {by}, {1}};

        // 进行坐标转换
        Matrix pointA = TInverse.times(new Matrix(pointB));

        // 提取转换后的坐标
        double ax = pointA.get(0, 0);
        double ay = pointA.get(1, 0);

        // 转换后的坐标
        return new Point2D(ax,ay);
    }
}

