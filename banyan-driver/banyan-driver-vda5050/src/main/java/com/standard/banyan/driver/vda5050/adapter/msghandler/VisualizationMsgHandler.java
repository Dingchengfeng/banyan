package com.standard.banyan.driver.vda5050.adapter.msghandler;

import com.alibaba.fastjson.JSON;
import com.standard.banyan.driver.amr.MessageHandler;
import com.standard.banyan.driver.vda5050.adapter.message.visualization.Visualization;

/**
 * @author dingchengfeng
 * @description TODO
 * @date 2023/10/23
 */
public class VisualizationMsgHandler implements MessageHandler {
    @Override
    public void handleMessage(String message) {
        Visualization visualization = JSON.parseObject(message, Visualization.class);

    }
}
