package com.standard.banyan.common.exception;

import com.standard.banyan.common.api.IErrorCode;
import lombok.Getter;

/**
 * 基础异常
 * @author dingchengfeng
 */
@Getter
public class BasicException extends RuntimeException {
    private IErrorCode errorCode;

    public BasicException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BasicException(String message) {
        super(message);
    }

    public BasicException(Throwable cause) {
        super(cause);
    }

    public BasicException(String message, Throwable cause) {
        super(message, cause);
    }

}
