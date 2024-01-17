package com.job.testsender.util;

import com.job.testsender.entity.User;
import com.job.testsender.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtUtilTest {

    private User mockedUser;

    private final String totallyExpiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUZXN0IiwiaWF0IjoxNzA0ODM5NzU2LCJleHAiOjE3MDQ4NDMzNTZ9.Q47YXq9VCmv4lvfEvBiHCP8S-V5qEbggZmrbDRIS57g";

    private JwtUtils jwtUtils;

    @BeforeAll
    public void init() {
        mockedUser = new User("TEST", "TEST");
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils,
                "SECRET_KEY",
                "23576d72294b643f624e6d3a737c2c7561633942306f64334c644e365f415e5f");
        ReflectionTestUtils.setField(jwtUtils,
                "accessTokenValidity",
                3600000);

    }

    @Test
    public void tokenGenerationTest() {
        String token = jwtUtils.generateToken(mockedUser);
        assertNotNull(token);
    }


    @Test
    public void tokenGeneration_Null_user_provided() {
        assertThrows(NullPointerException.class, () -> jwtUtils.generateToken(null),
                       "Provided UserDetails for token generation is null");
    }

    @Test
    public void tokenGeneration_Null_claims_provided() {
        assertThrows(IllegalArgumentException.class, () -> jwtUtils.generateToken(null, mockedUser));
    }

    @Test
    public void tokenGeneration_Validate_not_expired_token() {
        assertTrue(jwtUtils.isTokenValid(jwtUtils.generateToken(mockedUser), mockedUser));
    }

    @Test
    public void tokenGeneration_Validate_expired_token() {
        assertThrows(ExpiredJwtException.class, () ->
                jwtUtils.isTokenValid(totallyExpiredToken, mockedUser));
    }
}
