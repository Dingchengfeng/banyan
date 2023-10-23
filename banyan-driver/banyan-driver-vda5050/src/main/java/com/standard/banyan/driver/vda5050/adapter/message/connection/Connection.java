package com.standard.banyan.driver.vda5050.adapter.message.connection;

import com.standard.banyan.driver.vda5050.adapter.message.Header;


/**
 * 连接信息
 * @author dingchengfeng
 * @date 2023/10/23
 */
public class Connection extends Header {

    public static final String JSON_SCHEMA = "connection.schema";

    private State connectionState;

    public enum State {

        /**
         * 在线
         */
        ONLINE,
        /**
         * 正常断线
         */
        OFFLINE,
        /**
         * 意外断线
         */
        CONNECTIONBROKEN;
    }
}
