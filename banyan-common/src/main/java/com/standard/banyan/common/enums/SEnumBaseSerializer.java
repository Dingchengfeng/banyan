package com.standard.banyan.common.enums;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.lang.reflect.Type;

/**
 * @author dingchengfeng
 */
public class SEnumBaseSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.out;
        if (object instanceof SEnumBase) {
            out.writeString(((SEnumBase) object).getValue());
        } else {
            out.writeEnum((Enum<?>) object);
        }
    }
}
