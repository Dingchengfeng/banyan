package com.standard.banyan.kernel.resource.edge;

import com.tiansu.nts.rcs.imap.manage.resource.MapResource;
import com.tiansu.nts.rcs.math.Point2D;
import com.tiansu.nts.rcs.model.Edge;
import com.tiansu.nts.rcs.model.Node;
import com.tiansu.nts.rcs.model.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 跨地图连接的桥
 *
 * @author dingchengfeng
 * @description 跨地图连接的桥
 * @date 2023/07/06
 */
public class BridgeEdge extends MapResource implements Edge {
    //private final MapJson.Data.Bridge bridge;

    private Node startNode;

    private Node endNode;

    private Double cost = 10D;

    private final Edge.Type type;

    private final RobotDirection robotDirection;

    /**
     * 起点地图名
     */
    private final String mapName;

    public BridgeEdge(String mapName, String localId,Node startNode,Node endNode) {
        super(Resource.Name.EDGE, Resource.Type.MAP, mapName,localId);
        this.mapName = mapName;
        this.startNode = startNode;
        this.endNode = endNode;
        this.type = Edge.Type.LINE;
        this.robotDirection = RobotDirection.FORWARD;
    }

//    public BridgeEdge(String mapName, MapJson.Data.Bridge bridge) {
//        super(Resource.Name.EDGE, Resource.Type.MAP, mapName, bridge.getId());
//        this.bridge = bridge;
//        this.mapName = mapName;
//        this.type = Edge.Type.LINE;
//        this.robotDirection = RobotDirection.FORWARD;
//    }


    @Override
    public Double cost() {
        return cost;
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
        return startNode.getGlobalId();
    }

    @Override
    public Node getEndNode() {
        return  endNode;
    }

    @Override
    public GlobalId getEndNodeId() {
        return endNode.getGlobalId();
    }

    @Override
    public Double getStartExecuteTheta() {
        return null;
    }

    @Override
    public Double getEndExecuteTheta() {
        return null;
    }

    @Override
    public Edge.Type getEdgeType() {
        return type;
    }

    @Override
    public List<Point2D<Double>> getControlPoints() {
        return new ArrayList<>();
    }

    @Override
    public RobotDirection getRobotDirection() {
        return robotDirection;
    }

    @Override
    public Double getLength() {
        return 0D;
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
