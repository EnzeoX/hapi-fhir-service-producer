package com.job.testsender.controller;

import com.job.testsender.handler.FhirBundleMessageHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class MainController {

    private final FhirBundleMessageHandler fhirBundleMessageHandler;

    @PostMapping(value = "process-bundle", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> acquireMessage(@NonNull @RequestBody String message) {
        this.fhirBundleMessageHandler.collectAndProcessBundle(message);
        return ResponseEntity.ok("Bundle processed");
    }
}
