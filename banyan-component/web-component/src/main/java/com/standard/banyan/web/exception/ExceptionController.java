//package com.standard.allspark.web.exception;
//
//import com.alibaba.fastjson.JSON;
//import com.standard.allspark.web.common.ApiResult;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.web.ErrorProperties;
//import org.springframework.boot.autoconfigure.web.ServerProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.util.Assert;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.ServletWebRequest;
//import org.springframework.web.context.request.WebRequest;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Map;
//
//@RestController
//@EnableConfigurationProperties({ ServerProperties.class })
//@Slf4j
//public class ExceptionController implements ErrorController {
//
//     private ErrorAttributes errorAttributes;
//
//     @Autowired
//     private ServerProperties serverProperties;
//
//     /**
//      * 初始化ExceptionController
//      *
//      * @param errorAttributes
//      */
//     @Autowired
//     public ExceptionController(ErrorAttributes errorAttributes) {
//          Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
//          this.errorAttributes = errorAttributes;
//     }
//
//     @RequestMapping(value = "/error")
//     @ResponseBody
//     public ApiResult<Object> error(HttpServletRequest request) {
//          Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
//          HttpStatus status = getStatus(request);
//          ApiResult<Object> responseMsg = new ApiResult<>(status.value(), status.getReasonPhrase(), body.get("errors"));
//          log.error("exception detail:{}", JSON.toJSONString(body));
//          return responseMsg;
//     }
//
//     /**
//      * Determine if the stacktrace attribute should be included.
//      *
//      * @param request  the source request
//      * @param produces the media type produced (or {@code MediaType.ALL})
//      * @return if the stacktrace attribute should be included
//      */
//     protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
//          ErrorProperties.IncludeAttribute include = this.serverProperties.getError().getIncludeStacktrace();
//          if (ErrorProperties.IncludeAttribute.ALWAYS == include) {
//               return true;
//          }
//          if (ErrorProperties.IncludeAttribute.ON_PARAM == include) {
//               return getTraceParameter(request);
//          }
//          return false;
//     }
//
//     /**
//      * 获取错误的信息
//      *
//      * @param request
//      * @param includeStackTrace
//      * @return
//      */
//     private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
//          WebRequest webRequest = new ServletWebRequest(request);
//          return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
//     }
//
//     /**
//      * 是否包含trace
//      *
//      * @param request
//      * @return
//      */
//     private boolean getTraceParameter(HttpServletRequest request) {
//          String parameter = request.getParameter("trace");
//          if (parameter == null) {
//               return false;
//          }
//          return !"false".equals(parameter.toLowerCase());
//     }
//
//     /**
//      * 获取错误编码
//      *
//      * @param request
//      * @return
//      */
//     private HttpStatus getStatus(HttpServletRequest request) {
//          Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//          if (statusCode == null) {
//               return HttpStatus.INTERNAL_SERVER_ERROR;
//          }
//          try {
//               return HttpStatus.valueOf(statusCode);
//          } catch (Exception ex) {
//               return HttpStatus.INTERNAL_SERVER_ERROR;
//          }
//     }
//
////     /**
////      * 实现错误路径,暂时无用
////      *
////      * @return
////      */
////     @Override
////     public String getErrorPath() {
////          return "";
////     }
//}
