package com.standard.banyan.driver.vda5050.mqtt.topic;

import com.standard.banyan.driver.amr.MessageHandler;
import com.standard.banyan.driver.vda5050.adapter.msghandler.ConnectionMsgHandler;
import com.standard.banyan.driver.vda5050.adapter.msghandler.FactsheetMsgHandler;
import com.standard.banyan.driver.vda5050.adapter.msghandler.StateMsgHandler;
import com.standard.banyan.driver.vda5050.adapter.msghandler.VisualizationMsgHandler;
import lombok.AllArgsConstructor;

/**
 * 订阅主题
 * @author dingchengfeng
 * @date 2023/10/23
 */
@AllArgsConstructor
public enum SubscribeTopic implements Topic {
    /**
     * 连接
     */
    CONNECTION("connection",Qos.AT_LEAST_ONCE,new ConnectionMsgHandler()),
    /**
     * 模型信息
     */
    FACTSHEET("factsheet",Qos.AT_MOST_ONCE,new FactsheetMsgHandler()),
    /**
     * 状态
     */
    STATE("state",Qos.AT_MOST_ONCE,new StateMsgHandler()),
    /**
     * 可视化
     */
    VISUALIZATION("visualization",Qos.AT_MOST_ONCE,new VisualizationMsgHandler());


    private String topicName;
    private Qos qos;
    private MessageHandler messageHandler;

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public static SubscribeTopic matchTopic(String topic){
        for(SubscribeTopic subscribeTopic : SubscribeTopic.values()){
            if(topic.endsWith(subscribeTopic.topicName)){
                return subscribeTopic;
            }
        }
        return null;
    }
    @Override
    public String getTopicFilter() {
        return String.format(TopicFormat.VDA5050_TOPIC_FORMAT,"+","+",topicName);
    }

    @Override
    public Qos getQos() {
        return qos;
    }
}
