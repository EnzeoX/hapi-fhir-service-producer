package com.job.testsender.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.job.testsender.handler.FhirBundleMessageHandler;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

//@AutoConfigureMockMvc(addFilters = false)
//@EmbeddedKafka(
//        partitions = 1,
//        controlledShutdown = false,
//        brokerProperties = {
//                "listeners=PLAINTEXT://localhost:3333",
//                "port=3333"
//        })
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@WebMvcTest(controllers = MainController.class)
public class ControllerTest {

    @Autowired
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mvc;

    @MockBean
    private FhirBundleMessageHandler fhirBundleMessageHandler;

    @Test
    public void testGetMethod() throws Exception {
        mvc.perform(get("/api/v1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostMethod() throws Exception {
        mvc.perform(post("/api/v1/process-bundle")
                .contentType(MediaType.APPLICATION_JSON)
//                .content(body))
                .content(fromFile("Abdul218_Harris789_b0a06ead-cc42-aa48-dad6-841d4aa679fa.json")))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    private byte[] fromFile(String path) {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }
}
