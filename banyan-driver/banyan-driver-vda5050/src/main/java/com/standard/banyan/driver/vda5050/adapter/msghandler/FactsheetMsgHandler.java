package com.standard.banyan.driver.vda5050.adapter.msghandler;

import com.alibaba.fastjson.JSON;
import com.standard.banyan.driver.vda5050.mqtt.MessageHandler;
import com.standard.banyan.driver.vda5050.adapter.message.factsheet.Factsheet;

/**
 * @author dingchengfeng
 * @description TODO
 * @date 2023/10/23
 */
public class FactsheetMsgHandler implements MessageHandler {

    @Override
    public void handleMessage(String message) {
        Factsheet factsheet = JSON.parseObject(message, Factsheet.class);

    }
}
