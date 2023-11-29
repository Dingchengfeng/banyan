package com.standard.banyan.simulation.vda5050.msghandler;

import com.alibaba.fastjson.JSON;
import com.standard.banyan.simulation.vda5050.Vda5050Robot;
import com.standard.banyan.simulation.vda5050.entity.order.Order;

/**
 * @author dingchengfeng
 * @date 2023/11/08
 */
public class OrderMsgHandler implements MessageHandler {
    private final Vda5050Robot vda5050Robot;

    public OrderMsgHandler(Vda5050Robot vda5050Robot) {
        this.vda5050Robot = vda5050Robot;
    }

    @Override
    public void handleMessage(String message) {
        Order order = JSON.parseObject(message, Order.class);
        vda5050Robot.handleOrder(order);
    }
}
