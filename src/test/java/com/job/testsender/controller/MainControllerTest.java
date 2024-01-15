package com.job.testsender.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.job.testsender.handler.FhirBundleMessageHandler;
import com.job.testsender.service.UserService;
import com.job.testsender.util.HapiTestImports;
import com.job.testsender.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MainController.class)
@HapiTestImports
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private FhirBundleMessageHandler messageHandler;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetMethod() throws Exception {
        mvc.perform(get("/api/v1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testGetMethod_nonAuthorized() throws Exception { //should pass since auth for this method is disabled
        mvc.perform(get("/api/v1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testNullBody() throws Exception {
        mvc.perform(post("/api/v1/process-bundle").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Required request body is missing"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAcquireMessage() throws Exception {
        String body = "test message";
        mvc.perform(post("/api/v1/process-bundle").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());

        verify(messageHandler).collectAndProcessStringBundle(body);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAcquireMessage_badRequest() throws Exception {
        mvc.perform(post("/api/v1/process-bundle").with(csrf())
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(messageHandler);
    }

    @Test
    @WithAnonymousUser
    public void testAcquireMessage_nonAuthorized() throws Exception {
        String body = "test message";
        mvc.perform(post("/api/v1/process-bundle").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isUnauthorized());
        verifyNoInteractions(messageHandler);
    }
}
