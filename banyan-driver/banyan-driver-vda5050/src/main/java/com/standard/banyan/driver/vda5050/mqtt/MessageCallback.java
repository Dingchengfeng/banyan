package com.standard.banyan.driver.vda5050.mqtt;

import com.standard.banyan.driver.amr.DriverMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
/**
 * mqtt client 回调
 * @author dingchengfeng
 * @date  2021/11/3
 */
@Slf4j
public class MessageCallback implements MqttCallback {

    private final DefaultMqttClient  defaultMqttClient;

    private final DriverMessageHandler driverMessageHandler;

    public MessageCallback(DefaultMqttClient defaultMqttClient,DriverMessageHandler driverMessageHandler) {
        this.defaultMqttClient = defaultMqttClient;
        this.driverMessageHandler = driverMessageHandler;
    }

    /**
     * 主动或被动断开连接时的回调
     * @param disconnectResponse
     */
    @Override
    public void disconnected(MqttDisconnectResponse disconnectResponse) {
        defaultMqttClient.reconnect();
        log.error("mqttClient连接断开，msg={},returnCode={}",disconnectResponse.getException().getMessage(),disconnectResponse.getReturnCode());
    }

    /**
     * 客户端异常
     * @param exception
     */
    @Override
    public void mqttErrorOccurred(MqttException exception) {
        log.error("mqttClient异常，msg={},reasonCode={}",exception.getMessage(),exception.getReasonCode());
    }

    /**
     * 消息订阅到达
     * @param topic
     * @param message
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        log.info("收到消息:topic={},message={}",topic,message);
        try {
            // todo 如何处理消息，处理到什么程度
            driverMessageHandler.handleMessage(message.toString());
        } catch (Exception e) {
            log.info("发送失败,error={},topic={},msg={}",e.getMessage(),topic,message);
            return;
        }
    }

    /**
     * 消息发布完成
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttToken token) {
        //todo-dcf 是否需要通知上层应用
        log.info("消息发布成功:topic={},messageId={}",token.getTopics(),token.getMessageId());
    }

    /**
     * 连接成功
     * @param reconnect
     * @param serverURI
     */
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("连接成功:reconnect={},serverURI={}",reconnect,serverURI);
        String[] topicFilterArray  = new String[]{
                MqttTopicFilter.PREFIX_SHARE_LAND + MqttTopicFilter.CONNECTION,
                MqttTopicFilter.PREFIX_SHARE_LAND + MqttTopicFilter.STATE,
                MqttTopicFilter.PREFIX_SHARE_LAND + MqttTopicFilter.FACTSHEET,
                MqttTopicFilter.PREFIX_SHARE_LAND + MqttTopicFilter.VISUALIZATION,
        };
        int[] qosArray = new int[]{
                QosEnum.QOS1.ordinal(),
                QosEnum.QOS0.ordinal(),
                QosEnum.QOS0.ordinal(),
                QosEnum.QOS0.ordinal()
        };

        defaultMqttClient.subscribe(topicFilterArray,qosArray);
    }

    @Override
    public void authPacketArrived(int reasonCode, MqttProperties properties) {
        log.info("authPacketArrived");
    }

}
