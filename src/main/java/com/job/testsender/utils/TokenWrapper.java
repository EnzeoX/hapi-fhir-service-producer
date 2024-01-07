package com.job.testsender.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class TokenWrapper {

    public final static String BEARER = "Bearer ";
    public final static String AUTH_HEADER = "Authorization";

    private static final Map<String, String> contextPathTokens = new HashMap<>();

    static {
        contextPathTokens.put("/api/v1/process-bundle", "ProvidedTokenForBundleProcessing");
    }

    public String getTokenForPath(@NotNull(message = "Context path not provided") String contextPath) {
        return contextPathTokens.get(contextPath);
    }
}
