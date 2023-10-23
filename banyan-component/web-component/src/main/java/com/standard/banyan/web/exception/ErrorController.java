package com.standard.banyan.web.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.standard.banyan.web.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 异常返回
 *
 * @ignore
 * @author dingchengfeng
 * @date 2022-03-31
 */
@Slf4j
@RestController
public class ErrorController extends BasicErrorController {

	private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {
	};

	public ErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes, new ErrorProperties());
	}

	@RequestMapping
	@Override
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		HttpStatus status = this.getStatus(request);
		if (status == HttpStatus.NO_CONTENT) {
			return new ResponseEntity<>(status);
		}
		else {
			Map<String, Object> body = this.getErrorAttributes(request,
					this.getErrorAttributeOptions(request, MediaType.ALL));
			String message = String.format("%s -> %s", body.get("path"),
					body.get("error"));
			ApiResult<Object> r = new ApiResult<>(status.value(), message, null);
			String jsonString = JSON.toJSONString(r);
			return new ResponseEntity<>(JSON.parseObject(jsonString, MAP_TYPE_REFERENCE), status);
		}
	}

}
