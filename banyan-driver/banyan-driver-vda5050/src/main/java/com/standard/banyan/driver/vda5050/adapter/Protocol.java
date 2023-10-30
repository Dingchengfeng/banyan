package com.standard.banyan.driver.vda5050.adapter;

/**
 * @author dingchengfeng
 * @description 协议描述
 * @date 2023/10/27
 */
public class Protocol {

    public static final String ADAPT_DEVICE = "amr";

    public static final String NAME = "vda5050";

    public static final String VERSION_PREFIX = "v";

    public static final String MAJOR = "2";

    public static final String MINOR = "0";

    public static final String PATCH = "1";

    public static final String VERSION = String.join(".", VERSION_PREFIX + MAJOR, MINOR, PATCH);
}
