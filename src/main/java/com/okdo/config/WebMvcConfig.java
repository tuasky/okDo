package com.okdo.config;

import com.okdo.common.core.JacksonObjectMapper;
import com.okdo.controller.interceptor.LoginCheckInterceptor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig extends WebMvcConfigurationSupport {

    final LoginCheckInterceptor loginCheckInterceptor;

    private final List<String> loginCheckExcludePath = Arrays.asList(
            "/**/*.css",
            "/**/*.js",
            "/**/*.png",
            "/**/*.jpg",
            "/**/*.jpeg",
            "/**/plugins/**",
            "/index.html",
            "/user/login",
            "/user/sendCode",
            "/");

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:63342")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, messageConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .excludePathPatterns(loginCheckExcludePath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
