package com.standard.banyan.web.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求使用Fast Json
 * @author jigang.duan
 */
@Configuration
public class HttpFastJsonConfig {

    @Bean
    public HttpMessageConverter configureMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                // 保留map空的字段
//                SerializerFeature.WriteMapNullValue,
                // 将String类型的null转成""
//                SerializerFeature.WriteNullStringAsEmpty,
                // 将Number类型的null转成0
//                SerializerFeature.WriteNullNumberAsZero,
                // 将List类型的null转成[]
//                SerializerFeature.WriteNullListAsEmpty,
                // 将Boolean类型的null转成false
//                SerializerFeature.WriteNullBooleanAsFalse,
                // 避免循环引用
                // 格式化
//                SerializerFeature.PrettyFormat,
                SerializerFeature.DisableCircularReferenceDetect
        );
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        List<MediaType> mediaTypeList = new ArrayList<>();
        // 解决中文乱码问题，相当于在Controller上的@RequestMapping中加了个属性produces = "application/json"
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        return converter;
    }
}
