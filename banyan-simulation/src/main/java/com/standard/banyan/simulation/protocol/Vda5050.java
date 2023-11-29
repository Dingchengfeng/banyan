package com.standard.banyan.simulation.protocol;

/**
 * @author dingchengfeng
 * @date 2023/11/15
 */
public class Vda5050 implements Protocol {
    public static final ProtocolName NAME = ProtocolName.VDA5050;

    public static final String VERSION_PREFIX = "v";

    public static final String MAJOR = "2";

    public static final String MINOR = "0";

    public static final String PATCH = "1";

    public static final String VERSION = String.join(".", MAJOR, MINOR, PATCH);

    @Override
    public ProtocolName getName() {
        return NAME;
    }

    @Override
    public String getMajor() {
        return MAJOR;
    }

    @Override
    public String getMinor() {
        return MINOR;
    }

    @Override
    public String getPatch() {
        return PATCH;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getVersionPrefix() {
        return VERSION_PREFIX;
    }
}
