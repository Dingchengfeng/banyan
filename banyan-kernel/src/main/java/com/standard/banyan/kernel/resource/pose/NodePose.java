package com.standard.banyan.kernel.resource.pose;

import com.tiansu.nts.rcs.model.Node;

/**
 * 节点上的位姿
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public class NodePose implements Pose {
    private final Double theta;

    private final Node node;

    public NodePose(Double theta, Node node) {
        this.theta = theta;
        this.node = node;
    }

    @Override
    public String toString() {
        return "NodePose{" + "theta=" + theta + ", node=" + node + '}';
    }

    @Override
    public Double getTheta() {
        return theta;
    }

    @Override
    public Node getNode() {
        return node;
    }
}
