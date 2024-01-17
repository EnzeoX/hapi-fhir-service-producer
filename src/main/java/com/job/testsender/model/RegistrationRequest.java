package com.job.testsender.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegistrationRequest {
    private final String username;
    private final String password;
}
