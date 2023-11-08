package com.standard.banyan.simulation.mqtt.topic;

import com.standard.banyan.simulation.msghandler.InstantActionsMsgHandler;
import com.standard.banyan.simulation.msghandler.OrderMsgHandler;
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
    ORDER("order",Qos.AT_MOST_ONCE,false,new OrderMsgHandler()),
    /**
     * 模型信息
     */
    INSTANT_ACTIONS("instantActions",Qos.AT_MOST_ONCE,false,new InstantActionsMsgHandler());


    private String topicName;
    private Qos qos;
    private boolean retained;
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

    @Override
    public boolean isRetained() {
        return retained;
    }
}
