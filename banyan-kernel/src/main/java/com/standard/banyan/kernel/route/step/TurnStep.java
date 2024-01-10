package com.standard.banyan.kernel.route.step;

import com.tiansu.nts.rcs.model.Edge;
import com.tiansu.nts.rcs.model.Node;
import com.tiansu.nts.rcs.model.Resource;
import lombok.Getter;

/**
 * 原地旋转
 *
 * @author dingchengfeng
 * @description 原地旋转
 * @date 2023/07/10
 */
public class TurnStep extends AbstractStep {
    private final Resource.GlobalId nodeId;

    private final Node node;

    @Override
    public Double getEndTheta() {
        return endTheta;
    }

    private final Double endTheta;

    @Getter
    private final Double startTheta;

    public TurnStep(Double startTheta, Double endTheta, Node node) {
        this.node = node;
        this.endTheta = endTheta;
        this.startTheta = startTheta;
        this.nodeId = node.getGlobalId();
    }

    @Override
    public Resource.GlobalId getResourceId() {
        return nodeId;
    }

    @Override
    public Node getStartNode() {
        return node;
    }

    @Override
    public Node getEndNode() {
        return node;
    }

    @Override
    public Double getCost() {
        return 0D;
    }

    @Override
    public Boolean isNeedTurn() {
        return true;
    }

    @Override
    public Double getLength() {
        return 0D;
    }

    @Override
    public Edge getEdge() {
        return null;
    }


    @Override
    public boolean isNodeStep() {
        return true;
    }

    @Override
    public boolean isEdgeStep() {
        return false;
    }
}
