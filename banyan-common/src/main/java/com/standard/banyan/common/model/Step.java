package com.standard.banyan.common.model;

import java.util.concurrent.Flow;

/**
 * 运动步骤
 *
 * @author dingchengfeng
 * @description 车的运动步骤
 * @date 2023/07/07
 */
public interface Step {
    /**
     * 获取step对应的资源id
     *
     * @return Resource.GlobalId 资源id
     * @since 3.1.0
     */
    Resource.GlobalId getResourceId();

    /**
     * 起点
     *
     * @return Node 起点
     * @since 3.1.0
     */
    Node getStartNode();

    /**
     * 终点
     *
     * @return Node 终点
     * @since 3.1.0
     */
    Node getEndNode();

    /**
     * 代价
     *
     * @return Double 代价
     * @since 3.1.0
     */
    Double getCost();

    /**
     * 是否需要旋转
     *
     * @return Boolean
     * @since 3.1.0
     */
    Boolean isNeedTurn();

    /**
     * 运动步骤的长度
     * @return 长度
     */
    Double getLength();

    /**
     * 获取边
     * @return
     */
    Edge getEdge();
    /**
     * 获取step在整个路由中的索引
     * @return 索引
     */
    Integer getIndex();

    /**
     * 设置step在整个路由中的索引
     * @param index 索引
     */
    void setIndex(Integer index);

    /**
     * 是否是节点运动步骤
     * @return
     */
    boolean isNodeStep();

    /**
     * 是否是边的运动步骤
     * @return
     */
    boolean isEdgeStep();

    /**
     * 获取最终角度
     * @return 最终角度
     */
    Double getEndTheta();
    /**
     * 查询前置流程
     * @return
     */
    default Flow getPreFlow(){
        return null;
    }
    /**
     * 查询后置流程
     * @return
     */
    default Flow getPostFlow(){
        return null;
    }


}
