package com.job.testsender.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.testsender.model.GlobalErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class FilterExceptionHandler {

    public void handleError(@Nonnull HttpServletResponse response, RuntimeException e) throws IOException {
        String exceptionClass = e.getClass().getSimpleName();
        GlobalErrorResponse errorResponse = new GlobalErrorResponse();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        switch (exceptionClass) {
            case "AuthenticationException":
            case "MalformedJwtException":
                errorResponse.setStatus("Unauthorized");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                break;

            default:
                errorResponse.setStatus("Error");
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                break;
        }
        errorResponse.setMessage(e.getMessage());
        response.getWriter().write(convertObjectToJson(errorResponse));
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
