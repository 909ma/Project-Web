package com.wtf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.validation.Validator;
import java.util.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/favicon.ico");
    }
    

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // ����Ӵϴ�.
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // ����Ӵϴ�.
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // ����Ӵϴ�.
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        // ����Ӵϴ�.
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // ����Ӵϴ�.
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // ����Ӵϴ�.
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        // ����Ӵϴ�.
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // ����Ӵϴ�.
    }

    @Override
    public Validator getValidator() {
        // ����Ӵϴ�.
        return null;
    }
}
