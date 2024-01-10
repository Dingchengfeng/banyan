package com.standard.banyan.kernel.route.algorithm;

import com.tiansu.nts.rcs.model.Edge;
import com.tiansu.nts.rcs.model.Node;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 默认路径规划
 *
 * @author dingchengfeng
 * @description 基于Dijkstra
 * @date 2023/07/13
 */
public class DefaultPathPlanning implements PathPlanning {

    private SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge> graph = null;
    private DijkstraShortestPath<Node, DefaultWeightedEdge> shortestPathAlgo = null;

    /**
     * 刷新图结构
     *
     * @param edges 边集合
     * @since 3.1.0
     */
    @Override
    public void refreshGraph(Set<Edge> edges) {
        SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge> newGraph = new SimpleDirectedWeightedGraph<>(
            DefaultWeightedEdge.class);
        for (Edge edge : edges) {
            newGraph.addVertex(edge.getStartNode());
            newGraph.addVertex(edge.getEndNode());
            newGraph.addEdge(edge.getStartNode(), edge.getEndNode());
            newGraph.setEdgeWeight(edge.getStartNode(), edge.getEndNode(), edge.cost());
        }
        shortestPathAlgo = new DijkstraShortestPath<>(newGraph);
        this.graph = newGraph;
    }

    /**
     * 起搜索点到终点的路径
     *
     * @param startNode 起点
     * @param endNode 终点
     * @return List<String>
     * @since 3.1.0
     */
    @Override
    public List<Node> searchPath(Node startNode, Node endNode) {
        GraphPath<Node, DefaultWeightedEdge> path = shortestPathAlgo.getPath(startNode, endNode);
        if (Objects.isNull(path)) {
            return new ArrayList<>();
        } else {
            return path.getVertexList();
        }
    }
}
