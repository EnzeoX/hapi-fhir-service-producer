package com.job.testsender.handler;

import com.job.testsender.entity.User;
import com.job.testsender.model.LoginModel;
import com.job.testsender.model.LoginResponseModel;
import com.job.testsender.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
@ConditionalOnExpression("${security.jwt.enabled} and not ${security.simple-token.enabled}")
public class AuthorizationHandler {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public LoginResponseModel authUser(LoginModel model) {
        Objects.requireNonNull(model, "No login parameters provided");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        model.getUsername(),
                        model.getPassword())
        );
        User user = new User(authentication.getName(), "");
        String token = jwtUtils.createToken(user);
        return new LoginResponseModel(authentication.getName(), token);
    }
}
