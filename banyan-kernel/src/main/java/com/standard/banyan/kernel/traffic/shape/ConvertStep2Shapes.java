package com.standard.banyan.kernel.traffic.shape;

import com.tiansu.nts.rcs.kernel.cfg.TrafficConfig;
import com.tiansu.nts.rcs.kernel.route.step.CrossMapStep;
import com.tiansu.nts.rcs.kernel.route.step.MoveBezierStep;
import com.tiansu.nts.rcs.kernel.route.step.MoveLineStep;
import com.tiansu.nts.rcs.kernel.route.step.TurnStep;
import com.tiansu.nts.rcs.kernel.traffic.TrafficManager;
import com.tiansu.nts.rcs.math.Point2D;
import com.tiansu.nts.rcs.math.Vector;
import com.tiansu.nts.rcs.model.Step;
import com.tiansu.nts.rcs.util.ApplicationContextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingchengfeng
 * @description 计算运动步骤的图形
 * @date 2023/08/16
 */
public class ConvertStep2Shapes {
    /**
     * 采样间隔角度
     */
    public static final Double SAMPLING_INTERVAL_RAD = Math.PI / 6;

    public static List<Shape> convert(Step step, TrafficManager.AmrInfo amrInfo) {
        List<Shape> shapes = new ArrayList<>();
        if (step instanceof CrossMapStep) {
            return shapes;
        }
        if (step instanceof MoveBezierStep) {
            return genBezierShapes((MoveBezierStep)step, amrInfo);
        }
        if (step instanceof MoveLineStep) {
            return genLineShapes((MoveLineStep)step, amrInfo);
        }
        if (step instanceof TurnStep) {
            return genTurnShapes((TurnStep)step, amrInfo);
        }
        return shapes;
    }

    private static List<Shape> genBezierShapes(MoveBezierStep step, TrafficManager.AmrInfo amrInfo) {

        TrafficConfig trafficConfig = ApplicationContextUtils.getBean(TrafficConfig.class);

        Double keepDistanceLength = trafficConfig.keepDistanceLength();
        List<Point2D<Double>> points = new ArrayList<>();
        points.add(step.getEdge().getControlPoints().get(0));
        points.add(step.getEdge().getControlPoints().get(1));
        points.add(step.getEdge().getControlPoints().get(2));
        points.add(step.getEdge().getControlPoints().get(3));

        return BezierStep2Rectangle.calculateRectangle(points, amrInfo.getWholeLength(), amrInfo.getWholeWidth(),
            step.getResourceId(), keepDistanceLength, amrInfo.getCenterOffset());
    }

    private static List<Shape> genLineShapes(MoveLineStep step, TrafficManager.AmrInfo amrInfo) {
        List<Shape> shapes = new ArrayList<>(1);
        TrafficConfig trafficConfig = ApplicationContextUtils.getBean(TrafficConfig.class);

        Double keepDistanceLength = trafficConfig.keepDistanceLength();
        Double theta = step.getEdge().getStartExecuteTheta();

        Point2D<Double> center = new Point2D<>(step.getStartNode().getX() / 2 + step.getEndNode().getX() / 2,
            step.getStartNode().getY() / 2 + step.getEndNode().getY() / 2);

        center = Rectangle.calculateCenterAfterOffset(center, amrInfo.getCenterOffset(), theta);

        center = Rectangle.calculateCenterAfterFrontIncrease(center, keepDistanceLength, theta);
        Double length = step.getStartNode().distanceTo(step.getEndNode()) + amrInfo.getAmrLength() + keepDistanceLength;
        shapes.add(new Rectangle(theta, length, amrInfo.getAmrWidth(), center, step.getResourceId()));
        return shapes;
    }

    private static List<Shape> genTurnShapes(TurnStep step, TrafficManager.AmrInfo amrInfo) {
        List<Shape> shapes = new ArrayList<>();

        if (step.isNeedTurn()) {
            Vector<Double> startVector = new Vector<>(Math.cos(step.getStartTheta()), Math.sin(step.getStartTheta()));
            Vector<Double> endVector = new Vector<>(Math.cos(step.getEndTheta()), Math.sin(step.getEndTheta()));
            Double turnRad = startVector.calculateRadian(endVector);
            Integer direction = startVector.calculateDirection(endVector);
            // 采样数
            int samplingNum = (int)Math.ceil(Math.abs(turnRad) / SAMPLING_INTERVAL_RAD);
            if (samplingNum > 0) {
                for (int i = 0; i < samplingNum; i++) {
                    Double theta = step.getStartTheta() + direction * SAMPLING_INTERVAL_RAD * i;
                    Point2D<Double> center = Rectangle.calculateCenterAfterOffset(
                        new Point2D<>(step.getEndNode().getX(), step.getEndNode().getY()), amrInfo.getCenterOffset(),
                        theta);

                    shapes.add(new Rectangle(theta, amrInfo.getAmrLength(), amrInfo.getAmrWidth(), center,
                        step.getResourceId()));
                }
            }
        }

        Point2D<Double> center = Rectangle.calculateCenterAfterOffset(
            new Point2D<>(step.getEndNode().getX(), step.getEndNode().getY()), amrInfo.getCenterOffset(),
            step.getEndTheta());
        shapes.add(new Rectangle(step.getEndTheta(), amrInfo.getAmrLength(), amrInfo.getAmrWidth(), center,
            step.getResourceId()));

        return shapes;
    }
}
