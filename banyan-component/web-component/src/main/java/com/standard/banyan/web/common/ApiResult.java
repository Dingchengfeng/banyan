package com.standard.banyan.web.common;


import com.standard.banyan.common.api.IErrorCode;
import com.standard.banyan.common.i18n.I18nMessageUtil;
import com.standard.banyan.common.i18n.LanguageEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.io.Serializable;

/**
 * 接口返回结果封装
 * @author jigang.duan
 */
@Getter
@Setter
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 4518290031778225230L;

    /**
     * 返回码，0 正常
     */
    private int code = 0;

    /**
     * 返回信息
     */
    private String message = "成功";

    /**
     * 返回数据
     */
    private T data;

    /**
     * api 返回结果
     */
    public ApiResult() {
    }

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * api 返回结果,区分多语言
     *
     * @param language 语言类型,eg: en_us 表示美式英文
     */
    public ApiResult(String language) {
        this.code = ResultCode.SUCCESS.getCode();
        language = LanguageEnum.getLanguageType(language);
        try {
            this.message = I18nMessageUtil.getMessage(language, ResultCode.SUCCESS.getMessage(),"SUCCESS");
        } catch (IOException e) {
            this.message = "SUCCESS";
        }
    }

    public static <T> ApiResult<T> success(T data) {
       return success(data, LocaleContextHolder.getLocale().toString());
    }

    /**
     * 获取成功状态结果,区分多语言(默认简体中文)
     *
     * @param data 返回数据
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static <T> ApiResult<T> success(T data, String language) {
        ApiResult<T> result = new ApiResult<T>(language);
        result.data = data;
        return result;
    }

    public static ApiResult failure(IErrorCode responseCode) {
        return failure(responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> ApiResult<T> failure(IErrorCode responseCode, T data) {
        return failure(responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static ApiResult failure(int code, String msg) {
        return failure(code ,msg, null);
    }

    /**
     * 获取失败状态结果,区分多语言(默认中文)
     *
     * @param code 返回状态码
     * @param msg 错误信息
     * @param data 数据
     * @return
     */
    public static <T> ApiResult<T> failure(int code, String msg, T data) {
        return failure(code, msg, data, LocaleContextHolder.getLocale().toString());
    }

    /**
     * 获取失败返回结果,区分多语言(默认中文)
     *
     * @param code 错误码
     * @param msg 错误信息
     * @param data 返回结果
     * @param language 语言类型,eg: en 表示英文
     * @return
     */
    public static <T> ApiResult<T> failure(int code, String msg, T data, String language) {
        ApiResult<T> result = new ApiResult<T>(language);
        language = LanguageEnum.getLanguageType(language);
        try {
            msg = I18nMessageUtil.getMessage(language, msg, msg);
        } catch (IOException e) {
            msg = "Error";
        }
        result.code = code;
        result.message = msg;
        result.data = data;
        if (data instanceof String) {
            String m = (String) data;
            if (!m.matches("^.*error$")) {
                m += "error";
            }
        }
        return result;
    }

}
