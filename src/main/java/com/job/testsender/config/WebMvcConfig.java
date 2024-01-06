package com.job.testsender.config;

import com.job.testsender.config.interceptor.TokenInterceptor;
import com.job.testsender.utils.TokenWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(new TokenWrapper())).addPathPatterns("/api/v1/process-bundle");
    }

}
