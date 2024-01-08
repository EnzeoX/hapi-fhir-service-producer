package com.job.testsender.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String username;
    private String password;
}
