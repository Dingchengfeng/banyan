package com.standard.banyan.web.common;

import com.standard.banyan.common.api.IErrorCode;
import lombok.Getter;

/**
 * 接口返回枚举
 * @author jigang.duan
 */
@Getter
public enum ResultCode implements IErrorCode {

    // 成功
    SUCCESS(0, "api.response.code.success"),
    FAIL(-1, "api.response.code.fail"),

    // 公共参数
    PARAM_ERROR(1001, "api.response.code.paramError"),
    LANGUAGE_TYPE_ERROR(1002, "api.response.code.languageTypeError"),


    UNKNOWN_ERROR(500,"api.response.code.unknownError");

    // 返回码
    private final int code;

    // 返回信息
    private final String message;

    ResultCode(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
