package com.job.testsender.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestControllerExceptionAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        String exceptionName = e.getClass().getSimpleName();
        log.error("{}, {}", exceptionName, e.getMessage());
        switch (exceptionName) {
            case "StringIndexOutOfBoundsException":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provided incorrect string");
            case "NullPointerException":
            case "IllegalArgumentException":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            case "HttpMessageNotReadableException":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required request body is missing");
            case "AuthenticationException":
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
