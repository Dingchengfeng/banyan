package com.standard.banyan.kernel.resource.pose;

import com.tiansu.nts.rcs.model.Node;

/**
 * 位姿
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public interface Pose {
    /**
     * 位姿角度
     *
     * @return Float 角度
     * @since 3.1.0
     */
    Double getTheta();

    /**
     * 位姿节点
     *
     * @return Node 节点
     * @since 3.1.0
     */
    Node getNode();
}
