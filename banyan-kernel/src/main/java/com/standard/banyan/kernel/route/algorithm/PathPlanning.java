package com.standard.banyan.kernel.route.algorithm;

import com.tiansu.nts.rcs.model.Edge;
import com.tiansu.nts.rcs.model.Node;

import java.util.List;
import java.util.Set;

/**
 * 路径规划算法
 *
 * @author dingchengfeng
 * @description 路径规划算法
 * @date 2023/07/06
 */
public interface PathPlanning {
    /**
     * 刷新图结构
     *
     * @param edges 边
     */
    void refreshGraph(Set<Edge> edges);

    /**
     * 起搜索点到终点的路径
     *
     * @param startNode 起点
     * @param endNode 终点
     * @return List<String> 列表
     */
    List<Node> searchPath(Node startNode, Node endNode);
}
