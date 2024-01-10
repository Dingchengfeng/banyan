package com.standard.banyan.kernel.route.step;

import com.tiansu.nts.rcs.imap.manage.resource.edge.MapEdge;
import com.tiansu.nts.rcs.model.*;

/**
 * 直线运动
 *
 * @author dingchengfeng
 * @description 直线运动
 * @date 2023/07/10
 */
public class MoveLineStep extends AbstractStep {
    private final Edge edge;

    private final Resource.GlobalId resourceId;

    private final Double cost;

    private Flow preFlow;

    private Flow postFlow;

    public MoveLineStep(Edge edge) {
        this.edge = edge;
        this.cost = edge.cost();
        this.resourceId = edge.getGlobalId();
        if(edge instanceof MapEdge){
            MapEdge mapEdge = (MapEdge)edge;
            FlowInfo preFlowInfo = mapEdge.getPreFlowInfo();
            if(preFlowInfo != null){
                preFlow = new Flow(preFlowInfo.getFlowName(),preFlowInfo.getDeviceId());
            }

            FlowInfo postFlowInfo = mapEdge.getPostFlowInfo();
            if(postFlowInfo != null){
                postFlow = new Flow(postFlowInfo.getFlowName(),preFlowInfo.getDeviceId());
            }
        }
    }

    @Override
    public Resource.GlobalId getResourceId() {
        return resourceId;
    }

    @Override
    public Node getStartNode() {
        return edge.getStartNode();
    }

    @Override
    public Node getEndNode() {
        return edge.getEndNode();
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
        return edge.getLength();
    }

    @Override
    public Edge getEdge() {
        return edge;
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

    @Override
    public Flow getPreFlow(){
        return preFlow;
    }

    @Override
    public Flow getPostFlow(){
       return postFlow;
    }

}
