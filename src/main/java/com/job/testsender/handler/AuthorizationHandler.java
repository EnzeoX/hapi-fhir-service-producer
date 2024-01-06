package com.job.testsender.handler;

import com.job.testsender.entity.User;
import com.job.testsender.model.LoginModel;
import com.job.testsender.model.LoginResponseModel;
import com.job.testsender.service.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class AuthorizationHandler {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public LoginResponseModel authUser(LoginModel model) {
        Objects.requireNonNull(model, "No login parameters provided");
//        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            model.getUsername(),
                            model.getPassword())
            );
            User user = new User(authentication.getName(), "");
            String token = jwtUtils.createToken(user);
            return new LoginResponseModel(authentication.getName(), token);

//        } catch (BadCredentialsException e) {
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
//            throw new NullPointerException("Invalid username or password");
//        } catch (Exception e) {
//            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//        }
    }
}
