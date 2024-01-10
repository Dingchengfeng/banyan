package com.standard.banyan.kernel.resource.edge;

import com.tiansu.nts.rcs.imap.entity.MapJson;
import com.tiansu.nts.rcs.imap.manage.resource.MapResource;
import com.tiansu.nts.rcs.math.Point2D;
import com.tiansu.nts.rcs.model.Edge;
import com.tiansu.nts.rcs.model.FlowInfo;
import com.tiansu.nts.rcs.model.Node;
import com.tiansu.nts.rcs.model.Resource;
import com.tiansu.nts.rcs.util.NormalizeUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 地图上的边
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public class MapEdge extends MapResource implements Edge {
    private final MapJson.Data.Edge edge;

    /**
     * 类型
     */
    private final Edge.Type type;

    /**
     * 机器人方向
     */
    private final RobotDirection robotDirection;

    private Node startNode;

    private Node endNode;

    public MapEdge(String mapName, MapJson.Data.Edge edge) {
        super(Resource.Name.EDGE, Resource.Type.MAP, mapName, edge.getId().toString());
        this.edge = edge;
        this.type = Edge.Type.valueOf(edge.getType());
        this.robotDirection = RobotDirection.valueOf(edge.getRobotDirection());
    }

    @Override
    public Double cost() {
        return edge.getCost();
    }

    @Override
    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node node) {
        startNode = node;
    }

    @Override
    public GlobalId getStartNodeId() {
        return new GlobalId(Name.NODE, Resource.Type.MAP, getGlobalId().getMapName(), edge.getSNode().toString());
    }

    @Override
    public Node getEndNode() {
        return endNode;
    }

    @Override
    public List<Point2D<Double>> getControlPoints() {
        if (Edge.Type.BEZIER.equals(type)) {
            List<Point2D<Double>> list = new ArrayList<>();
            list.add(new Point2D<>(startNode.getX(),startNode.getY()));
            list.add(new Point2D<>(edge.getCx(),edge.getCy()));
            list.add(new Point2D<>(edge.getDx(),edge.getDy()));
            list.add(new Point2D<>(endNode.getX(),endNode.getY()));
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public void setEndNode(Node node) {
        endNode = node;
    }

    @Override
    public GlobalId getEndNodeId() {
        return new GlobalId(Name.NODE, Resource.Type.MAP, getGlobalId().getMapName(), edge.getENode().toString());
    }

    @Override
    public Double getStartExecuteTheta() {
        return NormalizeUtil.normalizeRad(edge.getSFacing() + robotDirection.getIncludedAngle());
    }

    @Override
    public Double getEndExecuteTheta() {
        return NormalizeUtil.normalizeRad(edge.getEFacing() + robotDirection.getIncludedAngle());
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
        return startNode.distanceTo(endNode);
    }

    public FlowInfo getPreFlowInfo(){
        return edge.getPreFlowInfo();
    }

    public FlowInfo getPostFlowInfo(){
        return edge.getPostFlowInfo();
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
