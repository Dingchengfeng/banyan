package com.standard.banyan.simulation.mqtt.topic;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
public interface MessageHandler {
    /**
     * 处理消息
     * @param message
     */
    void handleMessage(String message);
}
