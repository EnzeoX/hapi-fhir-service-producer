package com.job.testsender.service;

import com.job.testsender.entity.User;
import com.job.testsender.exception.UnauthorizedUserException;
import com.job.testsender.handler.AuthHandler;
import com.job.testsender.model.AuthRequest;
import com.job.testsender.model.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    public void testRegister_null_data_provided() {
        assertThrows(NullPointerException.class, () -> authService.register(null));
    }

    @Test
    public void testAuthenticate() {
        RegistrationRequest request = new RegistrationRequest("username", "password");
        authService.register(request);

        AuthRequest login = new AuthRequest("username", "password");
        authService.authenticate(login);
        verify(authHandler).authenticateUser(login);
    }

    @Test
    public void testAuthenticate_null_data_provided() {
        assertThrows(NullPointerException.class, () -> authService.authenticate(null));
    }

    @Test
    public void testAuthenticate_no_user_found() {
        assertThrows(UnauthorizedUserException.class,
                () -> authService.authenticate(new AuthRequest("T", "T")),
                "User not found");
    }
}
