package com.standard.banyan.common.enums;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * @author dingchengfeng
 */
public class EnumBaseDeserializer implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Integer value = parser.parseObject(Integer.class);
        T[] enumConstants = ((Class<T>) type).getEnumConstants();
        for (T constant : enumConstants) {
            if ((constant instanceof IEnumBase) && ((IEnumBase) constant).getValue().equals(value)) {
                return constant;
            }
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
