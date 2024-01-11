//package com.job.testsender.controller;
//
//import com.job.testsender.TestSenderApplication;
//import com.job.testsender.config.interceptor.FilterException;
//import com.job.testsender.config.interceptor.JwtAuthenticationFilter;
//import com.job.testsender.entity.User;
//import com.job.testsender.handler.FhirBundleMessageHandler;
//import com.job.testsender.service.UserService;
//import com.job.testsender.utils.JwtUtils;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@Import(SecurityConfiguration.class)
////@WebMvcTest(value = MainController.class, includeFilters = {
////        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtils.class)
////})
//@WebMvcTest(MainController.class)
////@Import(WebSecurityConfiguration.class)
//@AutoConfigureMockMvc
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TestSenderApplication.class)
//public class MainControllerTest {
//
//    private static User mockedUser;
//    private static String jwtToken;
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private FhirBundleMessageHandler fhirBundleMessageHandler;
////
//    @MockBean
//    private FilterException filterException;
//
//    @MockBean
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
////    @MockBean
////    private AuthenticationProvider authenticationProvider;
//
////    @BeforeAll
////    public void init() {
////        mockedUser = new User("Test", "pass");
////        //accessTokenValidity
//////        ReflectionTestUtils.setField(jwtUtils, "SECRET_KEY", "23576d72294b643f624e6d3a737c2c7561633942306f64334c644e365f415e5f");
//////        ReflectionTestUtils.setField(jwtUtils, "accessTokenValidity", 3600000);
////    }
//
////    private MockMvc mockMvc;
////
////    @Autowired
////    private WebApplicationContext webApplicationContext;
////
////    @Autowired
////    private FilterChainProxy springSecurityFilterChain;
//
////    @BeforeAll
////    public void setup() throws Exception {
////        mvc = MockMvcBuilders
////                .webAppContextSetup(webApplicationContext)
////                .addFilter(springSecurityFilterChain)
////                .build();
////    }
//
//    @Test
//    @WithAnonymousUser
//    public void testGetMethod() throws Exception {
//        mvc.perform(get("/api/v1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testPostMethod() throws Exception {
////        jwtUtils = new JwtUtils("23576d72294b643f624e6d3a737c2c7561633942306f64334c644e365f415e5f", 3600000);
////        ReflectionTestUtils.setField(jwtUtils, "SECRET_KEY", "23576d72294b643f624e6d3a737c2c7561633942306f64334c644e365f415e5f");
////        ReflectionTestUtils.setField(jwtUtils, "accessTokenValidity", 3600000);
//        jwtToken = jwtUtils.generateToken(mockedUser);
//        mvc.perform(post("/api/v1/process-bundle")
//                .contentType(MediaType.APPLICATION_JSON)
////                .content(fromFile("Abdul218_Harris789_b0a06ead-cc42-aa48-dad6-841d4aa679fa.json"))
//                .content("{}")
//                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testBundleProcessingExpiredTokenProvided() throws Exception {
//        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUZXN0IiwiaWF0IjoxNzA0ODM5NzU2LCJleHAiOjE3MDQ4NDMzNTZ9.Q47YXq9VCmv4lvfEvBiHCP8S-V5qEbggZmrbDRIS57g";
//        mvc.perform(post("/api/v1/process-bundle")
//                .contentType(MediaType.APPLICATION_JSON)
////                .content(fromFile("Abdul218_Harris789_b0a06ead-cc42-aa48-dad6-841d4aa679fa.json"))
//                .content("{}")
//                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", expiredToken)))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    public void testBundleProcessingNoTokenProvided() throws Exception {
//        mvc.perform(post("/api/v1/process-bundle")
//                .contentType(MediaType.APPLICATION_JSON)
////                .content(fromFile("Abdul218_Harris789_b0a06ead-cc42-aa48-dad6-841d4aa679fa.json"))
//                .content("{}"))
//                .andExpect(status().isForbidden());
//    }
//
//    @SneakyThrows
//    private byte[] fromFile(String path) {
//        return new ClassPathResource(path).getInputStream().readAllBytes();
//    }
//}


package com.job.testsender.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.job.testsender.handler.FhirBundleMessageHandler;
import com.job.testsender.service.UserService;
import com.job.testsender.util.HapiTestImports;
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
    public void testAcquireMessage() throws Exception {
        String body = "test message";
        mvc.perform(post("/api/v1/process-bundle").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());

        verify(messageHandler).collectAndProcessBundle(body);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAcquireMessage_badRequest() throws Exception {
        mvc.perform(post("/api/v1/process-bundle").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); //Mykola, just for you. Without body should return BadRequest 400, not 500

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
