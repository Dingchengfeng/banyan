package com.standard.banyan.driver.vda5050.adapter.message.connection;

import com.standard.banyan.driver.vda5050.adapter.message.Header;
import lombok.Getter;
import lombok.Setter;


/**
 * 连接信息
 * @author dingchengfeng
 * @date 2023/10/23
 */
@Getter
@Setter
public class Connection extends Header {

    public static final String JSON_SCHEMA = "connection.schema";

    /**
     * 状态
     */
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
