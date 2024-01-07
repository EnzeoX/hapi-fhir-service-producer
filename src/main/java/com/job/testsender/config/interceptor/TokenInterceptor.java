package com.job.testsender.config.interceptor;

import com.job.testsender.utils.TokenWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.job.testsender.utils.TokenWrapper.AUTH_HEADER;
import static com.job.testsender.utils.TokenWrapper.BEARER;

@Slf4j
@AllArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenWrapper tokenWrapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthenticationException {
        if (request.getHeader(AUTH_HEADER).length() < 7) {
            throw new NullPointerException("Provided authorization header is null or empty");
        }
        String requestToken = request.getHeader(AUTH_HEADER).substring(BEARER.length());
        if (!Objects.requireNonNull(tokenWrapper.getTokenForPath(request.getServletPath()), "No token for provided URI")
                .equals(requestToken)) {
            throw new AuthenticationException("Illegal token provided");
        }
        return true;
    }
}
