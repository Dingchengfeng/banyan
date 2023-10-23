package com.standard.banyan.common;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.standard.banyan.common.api.IErrorCode;
import com.standard.banyan.common.exception.BasicException;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 断言抛出相关异常
 * @author dingchengfeng
 */
public final class CommonAssert {

    private CommonAssert() {
    }

    public static void isTrue(boolean expression, IErrorCode errorCode) {
        Assert.isTrue(expression, () -> new BasicException(errorCode));
    }

    public static void isEqual(Object obj1, Object obj2, IErrorCode errorCode) {
        isTrue(ObjectUtil.equal(obj1, obj2), errorCode);
    }

    public static void notEqual(Object obj1, Object obj2, IErrorCode errorCode) {
        isTrue(ObjectUtil.notEqual(obj1, obj2), errorCode);
    }

    public static void isFalse(boolean expression, IErrorCode errorCode) {
        Assert.isFalse(expression, () -> new BasicException(errorCode));
    }

    public static void isNull(Object object, IErrorCode errorCode) {
        Assert.isNull(object, () -> new BasicException(errorCode));
    }

    public static <T> T notNull(T object, IErrorCode errorCode) {
        return Assert.notNull(object, () -> new BasicException(errorCode));
    }

    public static <T extends CharSequence> T notEmpty(T text, IErrorCode errorCode) {
        return Assert.notEmpty(text, () -> new BasicException(errorCode));
    }

    public static <T extends CharSequence> T notBlank(T text, IErrorCode errorCode) {
        return Assert.notBlank(text, () -> new BasicException(errorCode));
    }

    public static <T extends CharSequence> T notContain(CharSequence textToSearch, T substring, IErrorCode errorCode) {
        return Assert.notContain(textToSearch, substring, () -> new BasicException(errorCode));
    }

    public static <T> T[] notEmpty(T[] array, IErrorCode errorCode) {
        return Assert.notEmpty(array, () -> new BasicException(errorCode));
    }

    public static <T> T[] noNullElements(T[] array, IErrorCode errorCode) {
        return Assert.noNullElements(array, () -> new BasicException(errorCode));
    }

    public static <E, T extends Iterable<E>> T notEmpty(T collection, IErrorCode errorCode) {
        return Assert.notEmpty(collection, () -> new BasicException(errorCode));
    }

    public static <K, V, T extends Map<K, V>> T notEmpty(T map, IErrorCode errorCode) {
        return Assert.notEmpty(map, () -> new BasicException(errorCode));
    }

    public static void regMatcher(String key, String regexp, String msg) {
        if(!Pattern.compile(regexp).matcher(key).find()){
            throw new BasicException(new IErrorCode() {
                @Override
                public int getCode() {
                    return 12001;
                }

                @Override
                public String getMessage() {
                    return msg;
                }
            });
        }
    }

}
