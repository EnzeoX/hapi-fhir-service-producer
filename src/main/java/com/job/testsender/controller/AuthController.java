package com.job.testsender.controller;

import com.job.testsender.handler.AuthorizationHandler;
import com.job.testsender.model.LoginModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api")
@ConditionalOnExpression("${security.jwt.enabled} and not ${security.simple-token.enabled}")
public class AuthController {

    private final AuthorizationHandler authorizationHandler;

    @PostMapping(
            value = "/auth",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> requestAuth(@RequestBody LoginModel login) {
        return ResponseEntity
                .status(200)
                .body(authorizationHandler.authUser(login));
    }
}
