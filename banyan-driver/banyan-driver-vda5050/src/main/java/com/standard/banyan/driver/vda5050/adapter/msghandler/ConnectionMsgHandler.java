package com.standard.banyan.driver.vda5050.adapter.msghandler;

import com.alibaba.fastjson.JSON;
import com.standard.banyan.driver.amr.MessageHandler;
import com.standard.banyan.driver.vda5050.adapter.message.connection.Connection;

/**
 * @author dingchengfeng
 * @description TODO
 * @date 2023/10/23
 */
public class ConnectionMsgHandler implements MessageHandler {
    @Override
    public void handleMessage(String message) {
        Connection connection = JSON.parseObject(message, Connection.class);
    }
}
