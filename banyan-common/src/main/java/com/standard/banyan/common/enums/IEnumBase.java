package com.standard.banyan.common.enums;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * @author dingchengfeng
 */
@JSONType(serializer = EnumBaseSerializer.class)
public interface IEnumBase extends EnumBase {
    String getName();
    Integer getValue();
}
