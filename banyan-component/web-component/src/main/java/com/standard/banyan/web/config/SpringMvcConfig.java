package com.standard.banyan.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @author dingchengfeng
 */
@Configuration
public class SpringMvcConfig {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }


    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * 路径匹配配置
             */
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                configurer.setUseTrailingSlashMatch(true);
            }

            /**
             * 视图分发器配置
             */
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                configurer
                        // url 参数支持
                        .favorParameter(true)
                        // 自定义 url 参数
                        .parameterName("data-format")
                        // 忽略 Http Accept Header 的支持
                        .ignoreAcceptHeader(true)
                        // json => application/json
                        .mediaType("json", MediaType.APPLICATION_JSON)
                        // html => text/html
                        .mediaType("html", MediaType.TEXT_HTML);
            }

            /**
             * 将对于静态资源的请求转发到 Servlet 容器的默认处理静态资源的 Servlet
             * 因为将 Spring 的拦截模式设置为 "/" 时会对静态资源进行拦截
             */
            @Override
            public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//                configurer.enable();
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/**")
                        .addResourceLocations("classpath:/resources/")
                        .addResourceLocations("classpath:/static/");
            }

            /**
             * 跨原始资源共享配置
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(false)
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowedMethods("GET", "POST", "HEAD", "PUT", "DELETE");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(localeChangeInterceptor());
            }
        };
    }
}
