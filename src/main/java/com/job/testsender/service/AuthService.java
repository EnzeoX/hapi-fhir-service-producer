package com.job.testsender.service;

import com.job.testsender.handler.AuthHandler;
import com.job.testsender.model.AuthRequest;
import com.job.testsender.model.AuthResponse;
import com.job.testsender.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthHandler authHandler;

    public AuthResponse register(RegistrationRequest registration) {
        return authHandler.registerUser(registration);
    }

    public AuthResponse authenticate(AuthRequest login) {
        return authHandler.authenticateUser(login);
    }
}
