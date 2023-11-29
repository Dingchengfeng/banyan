package com.standard.banyan.simulation.vda5050.mqtt;

import com.standard.banyan.simulation.vda5050.Vda5050Robot;
import com.standard.banyan.simulation.vda5050.topic.TopicType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

import java.nio.charset.StandardCharsets;

/**
 * mqtt client 回调
 * @author dingchengfeng
 * @date  2021/11/3
 */
@Slf4j
public class MessageCallback implements MqttCallback {

    private final DefaultMqttClient defaultMqttClient;

    private final Vda5050Robot robot;

    public MessageCallback(DefaultMqttClient defaultMqttClient, Vda5050Robot robot) {
        this.defaultMqttClient = defaultMqttClient;
        this.robot = robot;
    }

    /**
     * 主动或被动断开连接时的回调
     *
     * @param disconnectResponse 断开时的相关属性
     */
    @Override
    public void disconnected(MqttDisconnectResponse disconnectResponse) {
        defaultMqttClient.reconnect();
        log.error("mqttClient连接断开，msg={},returnCode={}", disconnectResponse.getException().getMessage(),
            disconnectResponse.getReturnCode());
    }

    /**
     * 客户端异常
     *
     * @param exception 异常
     */
    @Override
    public void mqttErrorOccurred(MqttException exception) {
        log.error("mqttClient异常，msg={},reasonCode={}", exception.getMessage(), exception.getReasonCode());
    }

    /**
     * 消息订阅到达
     *
     * @param topic   主题
     * @param message 消息
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        //消息放到rocketMQ
        log.info("收到消息:topic={},message={}", topic, message);
        try {
            // 处理消息
            if (topic.endsWith(TopicType.ORDER.getName())) {
                robot.getOrderTopic().getMessageHandler()
                    .handleMessage(new String(message.getPayload(), StandardCharsets.UTF_8));
                return;
            }
            if (topic.endsWith(TopicType.INSTANT_ACTIONS.getName())) {
                robot.getInstantActionsTopic().getMessageHandler()
                    .handleMessage(new String(message.getPayload(), StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            log.info("发送失败,topic={},msg={},", topic, message,e);
        }
    }

    /**
     * 消息发布完成
     *
     * @param token 令牌
     */
    @Override
    public void deliveryComplete(IMqttToken token) {
        log.info("消息发布成功:topic={},messageId={}", token.getTopics(), token.getMessageId());
    }

    /**
     * 连接成功
     *
     * @param reconnect 重连标志
     * @param serverUri server地址
     */
    @Override
    public void connectComplete(boolean reconnect, String serverUri) {
        log.info("连接成功:reconnect={},serverURI={}", reconnect, serverUri);
        defaultMqttClient.subscribe(robot.getOrderTopic().getTopicFilter(), robot.getOrderTopic().getQos());
        defaultMqttClient.subscribe(robot.getInstantActionsTopic().getTopicFilter(),
            robot.getInstantActionsTopic().getQos());
    }

    @Override
    public void authPacketArrived(int reasonCode, MqttProperties properties) {
        log.info("authPacketArrived");
    }

}
