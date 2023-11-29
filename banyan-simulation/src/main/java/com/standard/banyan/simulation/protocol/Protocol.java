package com.standard.banyan.simulation.protocol;

import lombok.Getter;

/**
 * @author dingchengfeng
 * @date 2023/11/14
 */
@Getter
public enum Protocol {
    VDA5050("v","2","0","1");

    Protocol(String versionPrefix,String major, String minor, String patch) {
        this.versionPrefix = versionPrefix;
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    private final String versionPrefix;

    private final String major;

    private final String minor;

    private final String patch;


    /**
     * 完整版本
     * @return 完整版本
     */
    public String getVersion(){
        return String.join(".", major, minor, patch);
    }

}
