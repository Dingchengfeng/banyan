package com.standard.banyan.driver.vda5050.adapter.message.order;

import com.alibaba.fastjson.JSON;

/**
 * @author dingchengfeng
 * @description TODO
 * @date 2023/10/26
 */
public class OrderTest {

    public String buildOrder() {
        Order order = new Order();
        order.setHeaderId(1L);
        order.setTimestamp("2023-09-26T14:57.00.00Z");
        order.setVersion("2.0.0");
        order.setManufacturer("");
        order.setSerialNumber("");

        order.setOrderId("1");
        order.setOrderUpdateId(1L);
        return JSON.toJSONString(order);
    }
}
