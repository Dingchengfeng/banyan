package com.standard.banyan.kernel.route;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.tiansu.nts.rcs.imap.manage.Continent;
import com.tiansu.nts.rcs.imap.manage.MapManager;
import com.tiansu.nts.rcs.imap.manage.resource.edge.BridgeEdge;
import com.tiansu.nts.rcs.imap.manage.resource.edge.TempLineEdge;
import com.tiansu.nts.rcs.imap.manage.resource.pose.NodePose;
import com.tiansu.nts.rcs.imap.manage.resource.pose.Pose;
import com.tiansu.nts.rcs.imap.manage.resource.pose.Station;
import com.tiansu.nts.rcs.kernel.cfg.RouteConfig;
import com.tiansu.nts.rcs.kernel.route.algorithm.DefaultPathPlanning;
import com.tiansu.nts.rcs.kernel.route.algorithm.PathPlanning;
import com.tiansu.nts.rcs.kernel.route.step.CrossMapStep;
import com.tiansu.nts.rcs.kernel.route.step.MoveBezierStep;
import com.tiansu.nts.rcs.kernel.route.step.MoveLineStep;
import com.tiansu.nts.rcs.kernel.route.step.TurnStep;
import com.tiansu.nts.rcs.math.Point2D;
import com.tiansu.nts.rcs.model.Edge;
import com.tiansu.nts.rcs.model.Node;
import com.tiansu.nts.rcs.model.Step;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 路由
 *
 * @author dingchengfeng
 * @description 在多地图间做路由
 * @date 2023/07/24
 */
@Component
@Slf4j
public class WorldRouter implements Router {
    private PathPlanning pathPlanning = null;

    @Resource
    private MapManager mapManager;

    @Resource
    private RouteConfig routeConfig;

    /**
     * 初始化
     */
    @Override
    public void init() {
        refresh();
    }

    @Override
    public void refresh() {
        DefaultPathPlanning defaultPathPlanning = new DefaultPathPlanning();
        Set<Edge> edges = new HashSet<>(128);
        Collection<Continent> continents = mapManager.getAllContinentMap().values();
        for (Continent continent : continents) {
            edges.addAll(continent.getEdgeMap().values());
        }
        edges.addAll(mapManager.getAllBridges());
        defaultPathPlanning.refreshGraph(edges);
        pathPlanning = defaultPathPlanning;
    }

    @Override
    public Route genRoute(String amrId, Pose startPose, Pose endPose) {
        log.info("生成完整路由参数:amrId={},startPose={},endPose={}", amrId, startPose, endPose);
        Node bestReturnNode = findBestReturnNode(amrId, startPose);
        if (Objects.isNull(bestReturnNode)) {
            log.warn("机器人无法回归:amrId={},startPose={}", amrId, startPose);
            return null;
        }
        List<Node> nodeList = pathPlanning.searchPath(bestReturnNode, endPose.getNode());

        List<Step> steps = praiseNodesToSteps(new NodePose(null, bestReturnNode), endPose, nodeList);
        if (CollUtil.isEmpty(steps)) {
            log.info("从回归点到终点路径不可达:amrId={},bestReturnNode={},endPose={}", amrId, bestReturnNode, endPose);
            return null;
        }
        if (bestReturnNode.distanceTo(startPose.getNode()) < routeConfig.minReturnDistance()) {
            log.info("不需要回归的完整路由:steps={}", steps);
            return new Route(steps);
        } else {
            Double endTheta = (steps.get(0)).getEndTheta();
            List<Step> returnSteps = genReturnSteps(amrId, startPose, new NodePose(endTheta, bestReturnNode));
            returnSteps.addAll(steps.subList(1, steps.size()));
            log.info("需要回归的完整路由:steps={}", returnSteps);
            return new Route(returnSteps);
        }

    }

    private List<Step> praiseNodesToSteps(Pose startPose, Pose endPose, List<Node> nodeList) {
        List<Step> steps = new ArrayList<>();
        if (CollUtil.isEmpty(nodeList)) {
            log.info("路径不可达");
            return steps;
        }
        if (nodeList.size() == 1) {
            steps.add(genTurnStep(startPose.getTheta(), endPose.getTheta(), endPose.getNode()));
            return steps;
        }

        Double startTheta = startPose.getTheta();
        for (int i = 0; i < nodeList.size() - 1; i++) {
            Edge edge = mapManager.getEdge(nodeList.get(i), nodeList.get(i + 1));
            genStepForEdge(steps, startTheta, edge);
            startTheta = edge.getEndExecuteTheta();
        }
        steps.add(genTurnStep(startTheta, endPose.getTheta(), endPose.getNode()));
        return steps;
    }

    private void genStepForEdge(List<Step> steps, Double startTheta, Edge edge) {
        if (edge instanceof BridgeEdge) {
            // 跨地图的虚拟边起点处不需要旋转
            steps.add(genTurnStep(startTheta, startTheta, edge.getStartNode()));
            steps.add(new CrossMapStep((BridgeEdge)edge));
            return;
        }
        if (Edge.Type.LINE.equals(edge.getEdgeType())) {
            steps.add(genTurnStep(startTheta, edge.getStartExecuteTheta(), edge.getStartNode()));
            steps.add(new MoveLineStep(edge));
            return;
        }
        if (Edge.Type.BEZIER.equals(edge.getEdgeType())) {
            steps.add(genTurnStep(startTheta, edge.getStartExecuteTheta(), edge.getStartNode()));
            steps.add(new MoveBezierStep(edge));
        }
    }

    private Step genTurnStep(Double startTheta, Double endTheta, Node node) {
        if (Objects.isNull(startTheta)) {
            startTheta = endTheta;
        }
        return new TurnStep(startTheta, endTheta, node);
    }

    @Override
    public Double calculateCost(String amrId, Pose startPose, Pose endPose) {
        Node bestReturnNode = findBestReturnNode(amrId, startPose);
        return calculateCostByNode(bestReturnNode, endPose.getNode());
    }

    @Nullable
    private Double calculateCostByNode(Node startNode, Node endNode) {
        Double cost = 0D;
        List<Node> nodeList = pathPlanning.searchPath(startNode, endNode);
        if (CollectionUtil.isEmpty(nodeList)) {
            return null;
        }
        if (nodeList.size() == 1) {
            return cost;
        }
        for (int i = 0; i < nodeList.size() - 1; i++) {
            Edge edge = mapManager.getEdge(nodeList.get(i), nodeList.get(i + 1));
            cost = cost + edge.cost();
        }
        return cost;
    }

    @Override
    public Station findNearestEndStation(String amrId, Pose startPose, Set<Station> stations) {
        Double minCost = Double.MAX_VALUE;
        Station minCostStation = null;
        Node bestReturnNode = findBestReturnNode(amrId, startPose);
        for (Station station : stations) {
            Double cost = calculateCostByNode(bestReturnNode, station.getNode());
            if (cost != null && cost <= minCost) {
                minCostStation = station;
            }
        }
        return minCostStation;
    }

    @Override
    public Node findBestReturnNode(String amrId, Pose pose) {
        Edge bestReturnEdge = findBestReturnEdge(pose);
        if (Objects.isNull(bestReturnEdge)) {
            return null;
        }
        Node node = pose.getNode();
        Node startNode = bestReturnEdge.getStartNode();
        Node endNode = bestReturnEdge.getEndNode();
        if (node.distanceTo(startNode) < routeConfig.minReturnDistance()) {
            return startNode;
        }
        if (node.distanceTo(endNode) < routeConfig.minReturnDistance()) {
            return endNode;
        }

        //车体向量
        Vector<Double> amrVector = new Vector<>(Math.cos(pose.getTheta()), Math.sin(pose.getTheta()));
        //车到起点的向量
        Vector<Double> amrToStartVector = new Vector<>(startNode.getX() - node.getX(), endNode.getY() - node.getY());
        //车到终点的向量
        Vector<Double> amrToEndVector = new Vector<>(endNode.getX() - node.getX(), endNode.getY() - node.getY());

        if (Math.abs(amrVector.calculateRadian(amrToStartVector)) < Math.abs(
            amrVector.calculateRadian(amrToEndVector))) {
            return startNode;
        } else {
            return endNode;
        }
    }

    private Edge findBestReturnEdge(Pose pose) {
        double minDistance = Double.MAX_VALUE;
        Edge bestEdge = null;
        Continent continent = mapManager.getContinent(pose.getNode().getGlobalId().getMapName());
        Point2D<Double> aPoint = new Point2D<>(pose.getNode().getX(), pose.getNode().getY());

        for (Edge edge : continent.getEdges()) {
            Point2D<Double> bPoint = new Point2D<>(edge.getStartNode().getX(), edge.getStartNode().getY());
            Point2D<Double> cPoint = new Point2D<>(edge.getEndNode().getX(), edge.getEndNode().getY());
            Double distance = aPoint.distanceToLineSegment(bPoint, cPoint);
            if (distance < minDistance) {
                minDistance = distance;
                bestEdge = edge;
            }
        }
        if (minDistance > routeConfig.maxReturnDistance()) {
            // 无法回归
            return null;
        }

        return bestEdge;
    }

    @Override
    public List<Step> genReturnSteps(String amrId, Pose startPose, Pose endPose) {
        List<Step> steps = new ArrayList<>();
        Vector<Double> returnLineVector = new Vector<>(endPose.getNode().getX() - startPose.getNode().getX(),
            endPose.getNode().getY() - startPose.getNode().getY());
        steps.add(genTurnStep(startPose.getTheta(), returnLineVector.radian(), startPose.getNode()));
        steps.add(new MoveLineStep(new TempLineEdge(startPose.getNode(), endPose.getNode())));
        steps.add(genTurnStep(returnLineVector.radian(), endPose.getTheta(), endPose.getNode()));
        return steps;
    }

}
