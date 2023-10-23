package com.standard.banyan.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Bean拷贝相关工具类
 *
 * @author dingchengfeng
 * @date 2021/11/18 10:12 上午
 */
public class ColaBeanUtil {
    private ColaBeanUtil() {
    }


    public static <S, T> T copy(S source, Supplier<T> target) {
        T t = target.get();
        BeanUtils.copyProperties(source, t);
        return t;
    }
}
