package com.standard.banyan.kernel.resource.edge;

import com.tiansu.nts.rcs.imap.manage.resource.TempResource;
import com.tiansu.nts.rcs.math.Point2D;
import com.tiansu.nts.rcs.math.Vector;
import com.tiansu.nts.rcs.model.Edge;
import com.tiansu.nts.rcs.model.Node;
import com.tiansu.nts.rcs.model.Resource;
import com.tiansu.nts.rcs.util.NormalizeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 临时直线
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public class TempLineEdge extends TempResource implements Edge {
    private final Node start;

    private final Node end;

    private final Edge.Type type;

    private final RobotDirection robotDirection;

    private final Double sFacing;

    private final Double eFacing;

    private final Double length;

    public TempLineEdge(Node start, Node end) {
        super(Resource.Name.EDGE, Resource.Type.TEMP,start.getGlobalId().getMapName(), genLocalId(start, end));
        this.start = start;
        this.end = end;
        Vector<Double> vector = new Vector<>(end.getX() - start.getX(), end.getY() - start.getY());
        this.sFacing = vector.radian();
        this.eFacing = sFacing;
        this.length = start.distanceTo(end);
        this.type = Edge.Type.LINE;
        this.robotDirection = RobotDirection.FORWARD;
    }

    public static String genLocalId(Node start, Node end) {
        return '[' + start.getGlobalId().getLocalId() + '-' + end.getGlobalId().getLocalId() + ']';
    }

    @Override
    public Double cost() {
        return start.distanceTo(end);
    }

    @Override
    public Node getStartNode() {
        return start;
    }


    @Override
    public GlobalId getStartNodeId() {
        return getStartNode().getGlobalId();
    }

    @Override
    public Node getEndNode() {
        return end;
    }


    @Override
    public GlobalId getEndNodeId() {
        return end.getGlobalId();
    }

    @Override
    public Double getStartExecuteTheta() {
        return NormalizeUtil.normalizeRad(sFacing + robotDirection.getIncludedAngle());
    }

    @Override
    public Double getEndExecuteTheta() {
        return NormalizeUtil.normalizeRad(eFacing + robotDirection.getIncludedAngle());
    }

    @Override
    public Edge.Type getEdgeType() {
        return type;
    }

    @Override
    public RobotDirection getRobotDirection() {
        return robotDirection;
    }

    @Override
    public Double getLength() {
        return length;
    }

    @Override
    public List<Point2D<Double>> getControlPoints() {
        return new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
