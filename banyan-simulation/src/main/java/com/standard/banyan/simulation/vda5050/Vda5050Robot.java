package com.standard.banyan.simulation.vda5050;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.standard.banyan.common.math.Vector;
import com.standard.banyan.simulation.DriverConfig;
import com.standard.banyan.simulation.Robot;
import com.standard.banyan.simulation.vda5050.entity.common.AgvPosition;
import com.standard.banyan.simulation.vda5050.entity.connection.Connection;
import com.standard.banyan.simulation.vda5050.entity.order.Node;
import com.standard.banyan.simulation.vda5050.mqtt.DefaultMqttClient;
import com.standard.banyan.simulation.vda5050.mqtt.MessageCallback;
import com.standard.banyan.simulation.vda5050.msghandler.InstantActionsMsgHandler;
import com.standard.banyan.simulation.vda5050.msghandler.OrderMsgHandler;
import com.standard.banyan.simulation.protocol.Protocol;
import com.standard.banyan.simulation.protocol.Vda5050;
import com.standard.banyan.simulation.vda5050.entity.common.Velocity;
import com.standard.banyan.simulation.vda5050.entity.instantactions.InstantActions;
import com.standard.banyan.simulation.vda5050.entity.order.Edge;
import com.standard.banyan.simulation.vda5050.entity.order.Order;
import com.standard.banyan.simulation.vda5050.entity.state.*;
import com.standard.banyan.simulation.vda5050.topic.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * vda5050协议机器人
 *
 * @author dingchengfeng
 * @date 2023/11/14
 */
@Slf4j
@Getter
public class Vda5050Robot implements Robot {

    private final DefaultMqttClient defaultMqttClient;

    private final String manufacturer;

    private final String serialNumber;

    private final SubscribeTopic orderTopic;

    private final SubscribeTopic instantActionsTopic;

    private final PublishTopic connectionTopic;

    private final PublishTopic stateTopic;

    private final Protocol protocol;

    private final State state;

    private Order currentOrder;

    private volatile boolean stopFlag = false;

    private final Double speed = 1.0;

    private final Double omega = Math.PI / 2;

    private Long lastFinishedSequenceId = -1L;

    private static final Integer PERIOD = 200;

    public Vda5050Robot(DriverConfig driverConfig, String manufacturer, String serialNumber) {
        this.manufacturer = manufacturer;
        this.serialNumber = serialNumber;
        this.protocol = new Vda5050();

        this.defaultMqttClient = new DefaultMqttClient(driverConfig.getMqtt().getBrokerUrl(), serialNumber,
            driverConfig.getMqtt().getUsername(), driverConfig.getMqtt().getPassword());

        this.orderTopic = new SubscribeTopic(Topic.Qos.AT_MOST_ONCE, false,
            String.format(TopicFormat.VDA5050_TOPIC_FORMAT, protocol.getVersionPrefix() + protocol.getMajor(),
                manufacturer, serialNumber, TopicType.ORDER.getName()), new OrderMsgHandler(this));

        this.instantActionsTopic = new SubscribeTopic(Topic.Qos.AT_MOST_ONCE, false,
            String.format(TopicFormat.VDA5050_TOPIC_FORMAT, protocol.getVersionPrefix() + protocol.getMajor(),
                manufacturer, serialNumber, TopicType.INSTANT_ACTIONS.getName()), new InstantActionsMsgHandler(this));

        this.connectionTopic = new PublishTopic(Topic.Qos.AT_MOST_ONCE, false,
            String.format(TopicFormat.VDA5050_TOPIC_FORMAT, protocol.getVersionPrefix() + protocol.getMajor(),
                manufacturer, serialNumber, TopicType.CONNECTION.getName()), new AtomicLong(0));

        this.stateTopic = new PublishTopic(Topic.Qos.AT_MOST_ONCE, false,
            String.format(TopicFormat.VDA5050_TOPIC_FORMAT, protocol.getVersionPrefix() + protocol.getMajor(),
                manufacturer, serialNumber, TopicType.STATE.getName()), new AtomicLong(0));

        this.state = new State();
    }

    @Override
    public void start() {
        initState();
        initConnection();

        new Thread(this).start();
    }

    private synchronized void publishState() {
        calculateState(PERIOD / 1000D);
        state.setHeaderId(stateTopic.nextHeaderId());
        state.setTimestamp(Instant.now().toString());

        defaultMqttClient.publish(stateTopic, JSON.toJSONString(state));
    }

    private void initConnection() {
        Connection connectionMessage = new Connection();
        connectionMessage.setConnectionState(Connection.State.CONNECTIONBROKEN);
        connectionMessage.setHeaderId(connectionTopic.nextHeaderId());
        connectionMessage.setTimestamp(Instant.now().toString());
        connectionMessage.setVersion(protocol.getVersion());
        connectionMessage.setManufacturer(manufacturer);
        connectionMessage.setSerialNumber(serialNumber);
        //建立连接，设遗言
        defaultMqttClient.connect(new MessageCallback(defaultMqttClient, this), connectionTopic,
            JSON.toJSONString(connectionMessage));

        connectionMessage = new Connection();
        connectionMessage.setConnectionState(Connection.State.ONLINE);
        connectionMessage.setHeaderId(connectionTopic.nextHeaderId());
        connectionMessage.setTimestamp(Instant.now().toString());
        connectionMessage.setVersion(protocol.getVersion());
        connectionMessage.setManufacturer(manufacturer);
        connectionMessage.setSerialNumber(serialNumber);
        defaultMqttClient.publish(connectionTopic, JSON.toJSONString(connectionMessage));
    }

    private synchronized void initState() {
        state.setVersion(protocol.getVersionPrefix() + protocol.getVersion());
        state.setManufacturer(manufacturer);
        state.setSerialNumber(serialNumber);
        state.setOrderId("");
        state.setOrderUpdateId(0L);
        state.setLastNodeId("");
        state.setLastNodeSequenceId(0L);
        state.setNodeStates(new ArrayList<>());
        state.setEdgeStates(new ArrayList<>());
        state.setDriving(false);
        state.setPaused(false);
        state.setNewBaseRequest(false);
        state.setActionStates(new ArrayList<>());

        BatteryState batteryState = new BatteryState();
        batteryState.setBatteryCharge(100.0);
        batteryState.setCharging(false);
        state.setBatteryState(batteryState);

        state.setOperatingMode(OperatingMode.AUTOMATIC);
        state.setErrors(new ArrayList<>());
        state.setSafetyState(new SafetyState(EStop.NONE, false));

        AgvPosition agvPosition = new AgvPosition();
        agvPosition.setX(0.01);
        agvPosition.setY(-0.02);
        agvPosition.setMapId("test");
        agvPosition.setTheta(0.0);
        agvPosition.setPositionInitialized(true);
        agvPosition.setLocalizationScore(1.0);
        state.setAgvPosition(agvPosition);

        Velocity velocity = new Velocity(0.0, 0.0, 0.0);
        state.setVelocity(velocity);
    }

    @Override
    public void stop() {
        stopFlag = true;
        Connection connectionMessage = new Connection();
        connectionMessage.setConnectionState(Connection.State.OFFLINE);
        connectionMessage.setHeaderId(connectionTopic.nextHeaderId());
        connectionMessage.setTimestamp(Instant.now().toString());
        connectionMessage.setVersion(protocol.getVersion());
        connectionMessage.setManufacturer(manufacturer);
        connectionMessage.setSerialNumber(serialNumber);
        defaultMqttClient.disconnect();
    }

    @Override
    public void run() {
        while (!stopFlag) {
            try {
                Thread.sleep(PERIOD);
                publishState();
            } catch (Exception e) {
                log.error("报告状态错误");
            }
        }
    }

    public synchronized void handleOrder(Order order) {
        if (currentOrder == null || !currentOrder.getOrderId().equals(order.getOrderId())) {
            // 新任务
            if (currentOrderFinished()) {
                // 当前任务结束
                if (isNodeReachable(order.getNodes().get(0))) {
                    // 起点可达
                    state.getActionStates().clear();
                    acceptNewOrder(order);
                } else {
                    // todo 起点不可达
                }
            } else {
                // todo 当前任务还没结束
            }
        } else {
            // 追加任务
            if (order.getOrderUpdateId() > currentOrder.getOrderUpdateId()) {
                if (currentOrderFinished()) {
                    // 当前任务完成
                    if (Objects.equals(state.getLastNodeId(), order.getNodes().get(0).getNodeId()) && Objects.equals(
                        state.getLastNodeSequenceId(), order.getNodes().get(0).getSequenceId())) {
                        acceptNewOrder(order);
                    }
                } else {
                    // 当前任务没完成
                    NodeState oldBaseEnd = state.getNodeStates().get(state.getNodeStates().size() - 1);
                    if (order.getNodes().get(0).getNodeId().equals(oldBaseEnd.getNodeId())) {
                        acceptOrderUpdate(order);
                    } else {
                        // todo 路径不连续
                    }
                }
            } else if (order.getOrderUpdateId() < currentOrder.getOrderUpdateId()) {
                // todo 更新错误
            }
        }
    }

    public synchronized void handInstantActions(InstantActions instantActions) {

    }

    private boolean isNodeReachable(Node node) {
        // todo: 需完善
        return StrUtil.isEmpty(state.getLastNodeId()) || node.getNodeId().equals(state.getLastNodeId());
    }

    private boolean currentOrderFinished() {
        // todo: 需完善
        return currentOrder == null || (state.getNodeStates().isEmpty() && state.getEdgeStates().isEmpty());
    }

    private void acceptNewOrder(Order order) {
        log.info("Accepting new order {}", order.getOrderId());
        currentOrder = order;
        lastFinishedSequenceId = -1L;
        state.setOrderId(order.getOrderId());
        state.setOrderUpdateId(order.getOrderUpdateId());

        state.getNodeStates().clear();
        for (Node node : order.getNodes()) {
            NodeState nodeState = new NodeState(node.getNodeId(), node.getSequenceId(), node.getNodeDescription(),
                node.getNodePosition(), node.getReleased());
            state.getNodeStates().add(nodeState);
        }

        state.getEdgeStates().clear();
        for (Edge edge : order.getEdges()) {
            EdgeState edgeState = new EdgeState(edge.getEdgeId(), edge.getSequenceId(), edge.getEdgeDescription(),
                edge.getReleased(), edge.getTrajectory());
            state.getEdgeStates().add(edgeState);
        }

    }

    private void acceptOrderUpdate(Order order) {
        log.info("Order update. id: {}", order.getOrderUpdateId());
        currentOrder = order;
        state.setOrderId(order.getOrderId());
        state.setOrderUpdateId(order.getOrderUpdateId());
        for (Node node : order.getNodes().subList(1, order.getNodes().size())) {
            NodeState nodeState = new NodeState(node.getNodeId(), node.getSequenceId(), node.getNodeDescription(),
                node.getNodePosition(), node.getReleased());
            state.getNodeStates().add(nodeState);
        }

        for (Edge edge : order.getEdges()) {
            EdgeState edgeState = new EdgeState(edge.getEdgeId(), edge.getSequenceId(), edge.getEdgeDescription(),
                edge.getReleased(), edge.getTrajectory());
            state.getEdgeStates().add(edgeState);
        }
    }

    /**
     * 计算leftTime时间后机器人位置
     * 剩余时间
     *
     * @param leftTime
     */
    private synchronized void calculateState(Double leftTime) {
        if (leftTime <= 0) {
            return;
        }

        AgvPosition agvPosition = state.getAgvPosition();
        Double theta = agvPosition.getTheta();
        Double x = agvPosition.getX();
        Double y = agvPosition.getY();
        Vector<Double> amrVector = new Vector<>(Math.cos(theta), Math.sin(theta));

        List<NodeState> nodeStates = state.getNodeStates();
        List<EdgeState> edgeStates = state.getEdgeStates();
        if (CollUtil.isEmpty(nodeStates)) {
            state.setDriving(false);
            return;
        }
        if (nodeStates.size() == 1) {
            // todo 可以判断距离最后一个点的距离来设置
            state.setNewBaseRequest(true);
        } else {
            state.setNewBaseRequest(false);
        }
        state.setDriving(true);
        NodeState nodeState = nodeStates.get(0);
        EdgeState edgeState = null;
        if (CollUtil.isNotEmpty(edgeStates)) {
            edgeState = edgeStates.get(0);
        }

        if (nodeState.getSequenceId().equals(lastFinishedSequenceId + 1)) {
            Double nodeTheta = nodeState.getNodePosition().getTheta();
            Vector<Double> nodeVector = new Vector<>(Math.cos(nodeTheta), Math.sin(nodeTheta));

            Integer direction = amrVector.calculateDirection(nodeVector);
            Double radian = amrVector.calculateRadian(nodeVector);
            double moveTime = Math.abs(radian) / omega;

            if (moveTime >= leftTime) {
                state.getAgvPosition().setTheta(theta + direction * leftTime * omega);

                Velocity velocity = new Velocity(0.0, 0.0, omega);
                state.setVelocity(velocity);
                return;
            } else {
                state.setLastNodeId(nodeState.getNodeId());
                state.setLastNodeSequenceId(nodeState.getSequenceId());
                lastFinishedSequenceId = nodeState.getSequenceId();
                nodeStates.remove(0);
                calculateState(leftTime - moveTime);
            }
        } else if (edgeState != null && edgeState.getSequenceId().equals(lastFinishedSequenceId + 1)) {
            // todo 考虑曲线
            Double nodeX = nodeState.getNodePosition().getX();
            Double nodeY = nodeState.getNodePosition().getY();
            Double length = Math.hypot(nodeX - x, nodeY - y);
            double moveTime = length / speed;

            if (moveTime >= leftTime) {
                state.getAgvPosition().setX(
                    state.getAgvPosition().getX() + speed * leftTime * Math.cos(state.getAgvPosition().getTheta()));
                state.getAgvPosition().setY(
                    state.getAgvPosition().getY() + speed * leftTime * Math.sin(state.getAgvPosition().getTheta()));
                Velocity velocity = new Velocity(speed * Math.cos(theta), speed * Math.sin(theta), 0.0);
                state.setVelocity(velocity);
                return;
            } else {
                lastFinishedSequenceId = edgeState.getSequenceId();
                edgeStates.remove(0);
                calculateState(leftTime - moveTime);
            }
        }
    }


}
