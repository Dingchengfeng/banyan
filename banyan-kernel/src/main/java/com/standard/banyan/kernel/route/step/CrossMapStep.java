package com.standard.banyan.kernel.route.step;

import com.tiansu.nts.rcs.imap.manage.resource.edge.BridgeEdge;
import com.tiansu.nts.rcs.model.Edge;
import com.tiansu.nts.rcs.model.Node;
import com.tiansu.nts.rcs.model.Resource;
import lombok.Getter;

/**
 * 跨地图的步骤
 *
 * @author dingchengfeng
 * @description 跨地图的步骤
 * @date 2023/07/10
 */
public class CrossMapStep extends AbstractStep {

    private final BridgeEdge bridgeEdge;

    @Getter
    private final Edge.RobotDirection robotDirection;

    private final Resource.GlobalId resourceId;

    private final Double cost;

    private Integer index;

    public CrossMapStep(BridgeEdge bridgeEdge) {
        this.bridgeEdge = bridgeEdge;
        this.cost = bridgeEdge.cost();
        this.robotDirection = Edge.RobotDirection.FORWARD;
        this.resourceId = bridgeEdge.getGlobalId();
    }

    @Override
    public Resource.GlobalId getResourceId() {
        return resourceId;
    }

    @Override
    public Node getStartNode() {
        return bridgeEdge.getStartNode();
    }

    @Override
    public Node getEndNode() {
        return bridgeEdge.getEndNode();
    }

    @Override
    public Double getCost() {
        return cost;
    }

    @Override
    public Boolean isNeedTurn() {
        return false;
    }

    @Override
    public Double getLength() {
        return 0D;
    }

    @Override
    public Edge getEdge() {
        return bridgeEdge;
    }

    @Override
    public boolean isNodeStep() {
        return false;
    }

    @Override
    public boolean isEdgeStep() {
        return true;
    }

    @Override
    public Double getEndTheta() {
        return null;
    }

}
