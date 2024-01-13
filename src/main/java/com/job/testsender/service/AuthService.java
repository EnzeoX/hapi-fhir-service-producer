package com.job.testsender.service;

import com.job.testsender.exception.UnauthorizedUserException;
import com.job.testsender.handler.AuthHandler;
import com.job.testsender.model.AuthRequest;
import com.job.testsender.model.AuthResponse;
import com.job.testsender.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthHandler authHandler;

    public AuthResponse register(RegistrationRequest registration) {
        Objects.requireNonNull(registration, "No registration data provided");
        return authHandler.registerUser(registration);
    }

    public AuthResponse authenticate(AuthRequest login) {
        Objects.requireNonNull(login, "No login data provided");
        AuthResponse response = authHandler.authenticateUser(login);
        if (response == null) throw new UnauthorizedUserException("User not found");
        return response;
    }
}
