package com.job.testsender.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

//    @Bean
//    public FilterRegistrationBean<UrlBearerFilter> controllerFilter() {
//        FilterRegistrationBean<UrlBearerFilter> controllerFilter = new FilterRegistrationBean<>();
//        controllerFilter.setFilter(new UrlBearerFilter());
//        controllerFilter.addUrlPatterns("/api/v1/process-bundle");
//        controllerFilter.setOrder(1);
//        return controllerFilter;
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
