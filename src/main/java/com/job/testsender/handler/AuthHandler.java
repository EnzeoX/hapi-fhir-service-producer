package com.job.testsender.handler;

import com.job.testsender.entity.User;
import com.job.testsender.model.AuthRequest;
import com.job.testsender.model.AuthResponse;
import com.job.testsender.model.RegistrationRequest;
import com.job.testsender.service.UserService;
import com.job.testsender.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registerUser(RegistrationRequest registration) {
        User user = User.builder()
                .username(registration.getUsername())
                .password(passwordEncoder.encode(registration.getPassword()))
                .role(User.Role.USER)
                .build();
        userService.saveNewUser(user);
        return authenticateUser(user);
    }

    public AuthResponse authenticateUser(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        return userDetails != null ? authenticateUser(userDetails) : null;
    }

    private AuthResponse authenticateUser(UserDetails user) {
        return new AuthResponse(jwtUtils.generateToken(user));
    }
}
