package com.job.testsender.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class TokenInterceptor implements HandlerInterceptor {

    private final static String BEARER = "Bearer ";
    private final static String AUTH_HEADER = "Authorization";
    private final static String TOKEN = "TopSecretBearerToken";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (Objects.requireNonNull(request.getHeader(AUTH_HEADER), "No auth header provided").startsWith(BEARER)) {
            String requestToken = request.getHeader(AUTH_HEADER).substring(BEARER.length());
            return requestToken.equals(TOKEN);
        }

        return false;
    }
}
