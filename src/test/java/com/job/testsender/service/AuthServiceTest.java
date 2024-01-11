package com.job.testsender.service;

import com.job.testsender.handler.AuthHandler;
import com.job.testsender.model.AuthRequest;
import com.job.testsender.model.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class AuthServiceTest {

    private AuthService authService;

    @MockBean
    private AuthHandler authHandler;

    @BeforeEach
    public void init() {
        authService = new AuthService(authHandler);
    }

    @Test
    public void testRegister() {
        RegistrationRequest request = new RegistrationRequest("username", "password");
        authService.register(request);
        verify(authHandler).registerUser(request);
    }

    @Test
    public void testAuthenticate() {
        AuthRequest login = new AuthRequest("username", "password");
        authService.authenticate(login);
        verify(authHandler).authenticateUser(login);
    }
}
