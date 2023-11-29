package com.standard.banyan.simulation.protocol;

/**
 * @author dingchengfeng
 * @date 2023/11/14
 */
public interface Protocol {
    /**
     * 协议名称
     * @return 协议名称
     */
    ProtocolName getName();

    /**
     * 主版本
     * @return 主版本
     */
    String getMajor();

    /**
     * 次版本
     * @return 次版本
     */
    String getMinor();

    /**
     * 补丁版本
     * @return 补丁版本
     */
    String getPatch();

    /**
     * 完整版本
     * @return 完整版本
     */
    String getVersion();

    /**
     * 版本前缀
     * @return 版本前缀
     */
    String getVersionPrefix();

    /**
     * @author dingchengfeng
     * @date 2023/11/14
     */
    enum ProtocolName {
        VDA5050
    }
}
