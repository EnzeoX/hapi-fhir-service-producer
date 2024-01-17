package com.job.testsender.util;

import com.job.testsender.config.SecurityConfiguration;
import com.job.testsender.exception.handler.FilterExceptionHandler;
import com.job.testsender.utils.JwtUtils;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationProvider;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SecurityConfiguration.class, JwtUtils.class, FilterExceptionHandler.class, TestingAuthenticationProvider.class})
public @interface HapiTestImports {
}
