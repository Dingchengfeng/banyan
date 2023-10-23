package com.standard.banyan.common.enums;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * @author dingchengfeng
 */
@JSONType(serializer = SEnumBaseSerializer.class)
public interface SEnumBase extends EnumBase {
    String getName();
    String getValue();
}
