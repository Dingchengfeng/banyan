package com.standard.banyan.web.exception;

import com.standard.banyan.common.exception.BasicException;
import com.standard.banyan.common.i18n.I18nMessageUtil;
import com.standard.banyan.web.common.ApiResult;
import com.standard.banyan.web.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.UnexpectedTypeException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 拦截异常并统一处理
 * @author dingchengfeng
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult handleValidException(MethodArgumentNotValidException e) {
        return handleBadRequest(e.getBindingResult());
    }

    /**
     * 表单验证异常处理
     * 在controller上使用@valid注解，实体类的熟悉上使用@NotBlank("xxxx不能为空")
     * 如果参数校验不通过，就会报这个错误
     */
    @ExceptionHandler(value = BindException.class)
    public ApiResult handleValidException(BindException e) {
        return handleBadRequest(e.getBindingResult());
    }

    /**
     * 业务异常处理
     */
    private static ApiResult handleBadRequest(BindingResult bindingResult) {
        String message = ResultCode.PARAM_ERROR.getMessage();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = I18nMessageUtil.getMessage(fieldError.getField()) + fieldError.getDefaultMessage();
            }
        }
        return ApiResult.failure(ResultCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 处理自定义的业务异常
     * @param e 异常对象
     * @param request 请求
     * @return 返回异常
     */
    @ExceptionHandler(BasicException.class)
    public ApiResult resultExceptionHandler(BasicException e, HttpServletRequest request) {
        log.warn("发生业务异常！原因是: {}", e.getMessage());
        return ApiResult.failure(e.getErrorCode());
    }

    /**
     * 500异常处理
     * 拦截抛出的异常，@ResponseStatus：用来改变响应状态码
     * @param e 异常对象
     * @param request 请求
     * @return 返回异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<String> handlerThrowable(Exception e, HttpServletRequest request) {
        log.warn("发生未知异常！原因是: ", e);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw,true));
        String infoMsg = sw.toString();
        return ApiResult.failure(ResultCode.UNKNOWN_ERROR, infoMsg);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ApiResult handleUnexpectedTypeException(UnexpectedTypeException e, HttpServletRequest request) {
        log.warn("发生参数校验异常！原因是:", e);
        return ApiResult.failure(ResultCode.PARAM_ERROR.getCode(), e.getLocalizedMessage());
    }

    /**
    * 404异常处理
    */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResult handleNoHandlerFoundeException(NoHandlerFoundException e, HttpServletRequest request) {
        return ApiResult.failure(HttpStatus.NOT_FOUND.value(), e.getLocalizedMessage());
    }

    /**
     * 405异常处理
     * */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult handle405(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        return ApiResult.failure(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getLocalizedMessage());
    }

    /**
     * 415异常处理
     * */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiResult handle415(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        return ApiResult.failure(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), e.getLocalizedMessage());
    }

}
