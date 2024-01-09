package com.job.testsender.config;

import com.job.testsender.config.interceptor.JwtAuthenticationFilter;
import com.job.testsender.config.interceptor.FilterException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            FilterException filterException) throws Exception {
        http //disable csrf
                .csrf()
                .disable();
        http //set permissions
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/v1").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/authentication/**").permitAll()
                .anyRequest()
                .authenticated()
                .and();
        http //set session management
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        http // add jwt filter
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(filterException, LogoutFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
