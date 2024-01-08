package com.job.testsender.controller;

import com.job.testsender.model.AuthRequest;
import com.job.testsender.model.AuthResponse;
import com.job.testsender.model.RegistrationRequest;
import com.job.testsender.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping(
            value = "/auth",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> requestAuth(@RequestBody AuthRequest login) {
        return ResponseEntity.ok(authService.authenticate(login));
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuthResponse> requestRegistration(@RequestBody RegistrationRequest registration) {
        return ResponseEntity.ok(authService.register(registration));
    }
}
