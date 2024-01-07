package com.job.testsender.controller;

import com.job.testsender.handler.FhirBundleMessageHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MainController.class)
@WebAppConfiguration
public class ControllerTest {

//    @Autowired
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mvc;

    @MockBean
    private FhirBundleMessageHandler fhirBundleMessageHandler;

    @Test
    public void testGetMethod() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/")).andReturn();
        Assertions.assertEquals("Working", result.getResponse().getContentAsString());
    }
}
