package com.standard.banyan.driver.vda5050.adapter.message.factsheet;


import com.standard.banyan.driver.vda5050.adapter.message.Header;
import lombok.Getter;
import lombok.Setter;

/**
 * 当前AGV的一些详细的软硬件规格说明
 *
 * @author dingchengfeng
 * @date 2023/10/24
 */
@Getter
@Setter
public class Factsheet extends Header {
    public static final String JSON_SCHEMA = "Factsheet.schema";

    /**
     * 型号规格
     */
    private TypeSpecification typeSpecification;

    /**
     * AGV基本物理特性
     */
    private PhysicalParameters physicalParameters;

    /**
     * AGV的协议限制
     */
    private ProtocolLimits protocolLimits;

    /**
     * VDA5050协议支持的功能
     */
    private ProtocolFeatures protocolFeatures;

    /**
     * AGV几何结构的详细定义
     */
    private AgvGeometry agvGeometry;

    /**
     * 负载能力的抽象规范
     */
    private LoadSpecification loadSpecification;

    /**
     * todo 官方文档没有解释这个字段，而且文档中类型是JSON-object,但是schema中是int
     */
    private Integer localizationParameters;


}
