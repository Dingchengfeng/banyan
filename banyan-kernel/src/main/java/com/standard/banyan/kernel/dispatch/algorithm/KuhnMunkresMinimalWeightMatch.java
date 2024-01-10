package com.standard.banyan.kernel.dispatch.algorithm;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.matching.KuhnMunkresMinimalWeightBipartitePerfectMatching;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.SimpleWeightedBipartiteGraphMatrixGenerator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author dingchengfeng
 * @description KM最小权二分图完美匹配
 * @date 2023/07/27
 */
@Component
public class KuhnMunkresMinimalWeightMatch implements MatchAlgo {

    @Override
    public Map<String, String> match(List<String> firstPart, List<String> secondPart, double[][] costMatrix) {
        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        GraphGenerator<String, DefaultWeightedEdge, String> generator = new SimpleWeightedBipartiteGraphMatrixGenerator<String, DefaultWeightedEdge>()
                .first(firstPart).second(secondPart).weights(costMatrix);
        generator.generateGraph(graph);

        MatchingAlgorithm.Matching<String, DefaultWeightedEdge> match = new KuhnMunkresMinimalWeightBipartitePerfectMatching<>(
            graph, new LinkedHashSet<>(firstPart), new LinkedHashSet<>(secondPart)).getMatching();
        Set<DefaultWeightedEdge> edges = match.getEdges();
        Map<String, String> matchMap = new HashMap<>(16);
        for (DefaultWeightedEdge edge : edges) {
            String edgeSource = graph.getEdgeSource(edge);
            String edgeTarget = graph.getEdgeTarget(edge);
            matchMap.put(edgeSource, edgeTarget);
        }
        return matchMap;
    }
}
