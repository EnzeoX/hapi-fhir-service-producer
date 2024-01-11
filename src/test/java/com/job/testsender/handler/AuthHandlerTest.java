package com.job.testsender.handler;

import com.job.testsender.entity.User;
import com.job.testsender.model.AuthRequest;
import com.job.testsender.model.RegistrationRequest;
import com.job.testsender.service.UserService;
import com.job.testsender.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AuthHandlerTest {

    private AuthHandler handler;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserService userService;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void init() {
        handler = new AuthHandler(jwtUtils, userService, passwordEncoder, authenticationManager);
    }

    @Test
    public void testRegisterUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        User user = User.builder()
                .username("username")
                .password("password")
                .role(User.Role.USER)
                .build();

        handler.registerUser(new RegistrationRequest("username", "whocares?"));
        verify(userService).saveNewUser(user);
    }

    @Test
    public void testAuthenticateUser() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        handler.authenticateUser(authRequest);
        verify(authenticationManager).authenticate(any());
        verify(userService).loadUserByUsername("username");
    }
}

