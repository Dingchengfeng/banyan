package com.standard.banyan.common.model;

/**
 * 节点
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public interface Node extends Resource {
    /**
     * 节点x坐标
     *
     * @return Integer x坐标
     * @since 3.1.0
     */
    Double getX();

    /**
     * 节点y坐标
     *
     * @return Integer y坐标
     * @since 3.1.0
     */
    Double getY();

    /**
     * this到that的距离
     *
     * @param that 另一个点
     * @return Double 距离
     * @since 3.1.0
     */
    default Double distanceTo(Node that) {
        return Math.hypot(this.getX() - that.getX(), this.getY() - that.getY());
    }
}
