package com.standard.banyan.web;

import com.standard.banyan.common.i18n.I18nMessageUtil;
import com.standard.banyan.web.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 * @author dingchengfeng
 */
@RestController
@RequestMapping("/")
@Slf4j
public class TestController {


    /**
     * Hello
     * @deprecated
     * @return
     */
    @Deprecated
    @GetMapping
    public ApiResult<String> index() {
        return ApiResult.success("hello allspark " + I18nMessageUtil.getMessage("login.username"));
    }

}
