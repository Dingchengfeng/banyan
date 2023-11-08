package com.standard.banyan.driver.vda5050.mqtt;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
public interface MessageHandler {
    void handleMessage(String message);
}
