package com.standard.banyan.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dingchengfeng
 * @description 流程信息
 * @date 2023/08/25
 */
@Data
public class FlowInfo implements Serializable {

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 设备id
     */
    private String deviceId;

}
