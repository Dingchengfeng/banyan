package com.standard.banyan.simulation.vda5050.topic;

import com.standard.banyan.simulation.vda5050.msghandler.MessageHandler;
import lombok.AllArgsConstructor;


/**
 * @author dingchengfeng
 * @date 2023/11/14
 */
@AllArgsConstructor
public class SubscribeTopic implements Topic {
    private final Qos qos;

    private final boolean retained;

    private final String topicFilter;

    private final MessageHandler messageHandler;

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    @Override
    public String getTopicFilter() {
        return topicFilter;
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
