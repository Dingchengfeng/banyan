package com.standard.banyan.common.enums;

/**
 * @author dingchengfeng
 */
public enum OrderEnum {
    // 正序倒序
    // 降序
    desc,
    // 正序
    asc;

    public boolean isAsc() {
        return this == asc;
    }
}
