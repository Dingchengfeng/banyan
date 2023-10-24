package com.standard.banyan.driver.vda5050.adapter.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author dingchengfeng
 * @description TODO
 * @date 2023/10/23
 */
@Getter
@Setter
public abstract class Header implements Serializable {
    /**
     * 消息id
     * 发送端为每个主题单独维护，每发送一条消息就加1,重启后从0开始
     */
    private Long headerId;
    /**
     * 时间戳 ISO8601  (I.e. YYYY-MM-DDTHH:mm:ss.sssZ)
     */
    private Instant timestamp;
    /**
     * 协议版本 [Major].[Minor].[Patch]. eg:2.0.1
     */
    private String version;
    /**
     * 机器人厂商
     */
    private String manufacturer;
    /**
     * 机器人序列号
     */
    private String serialNumber;
}