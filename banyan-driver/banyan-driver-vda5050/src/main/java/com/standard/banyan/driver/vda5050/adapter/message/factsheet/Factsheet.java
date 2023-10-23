package com.standard.banyan.driver.vda5050.adapter.message.factsheet;


import com.standard.banyan.driver.vda5050.adapter.message.Header;
import lombok.Data;

/**
 * @author dengxx
 * @description 当前AGV的一些详细的软硬件信息
 * @date 2023/08/22
 */
@Data
public class Factsheet extends Header {
    public static final String JSON_SCHEMA = "Factsheet.schema";
    /**
     * 型号规格
     */
    private transient TypeSpecification typeSpecification;
    /**
     * 规定AGV的基本物理特性的一些参数
     */
    private transient PhysicalParameters physicalParameters;
    /**
     * AGV的协议限制
     */
    private transient ProtocolLimits protocolLimits;
    /**
     * VDA5050协议支持的功能
     */
    private transient ProtocolFeatures protocolFeatures;
    /**
     * AGV几何结构的详细定义
     */
    private transient AgvGeometry agvGeometry;
    /**
     * 负载能力的抽象规范
     */
    private transient LoadSpecification loadSpecification;
    /**
     * int类型 本地化详细规范
     */
    private transient Integer localizationParameters;


}
