package com.job.testsender.service;

import static org.junit.jupiter.api.Assertions.*;

import com.job.testsender.entity.User;
import com.job.testsender.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void init() {
        this.userService = new UserService(userRepository);
        createUsers();
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "username1";
        UserDetails userDetails = userService.loadUserByUsername(username);
        assertAll(
                () -> assertNotNull(userDetails),
                () -> assertEquals(username, userDetails.getUsername()),
                () -> assertEquals("password1", userDetails.getPassword()));
    }

    @Test
    public void testLoadUserByUsername_notExist() {
        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("not exist"));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    public void testSaveNewUser() {
        User user = new User(5L, "newUsername", "newPassword", User.Role.USER);
        assertAll(
                () -> assertDoesNotThrow(() -> userService.saveNewUser(user)),
                () -> assertEquals(user, userService.loadUserByUsername("newUsername"))
        );
    }

    @Test
    public void testSaveNewUser_alreadyExist() {
        User user = User.builder()
                .id(10L)
                .username("username1")
                .password("password1")
                .role(User.Role.USER)
                .build();

        assertThrows(DataIntegrityViolationException.class,
                () -> userService.saveNewUser(user));
    }

    @Test
    public void testSaveNewUser_emptyUser() {
        User user = new User();
        assertThrows(DataIntegrityViolationException.class,
                () -> userService.saveNewUser(user));
    }

    private void createUsers() {
        userRepository.save(new User(1L, "username1", "password1", User.Role.USER));
        userRepository.save(new User(2L, "Howard", "WhiteCarpet", User.Role.USER));
        userRepository.save(new User(3L, "Mike", "Nike", User.Role.USER));
        userRepository.save(new User(4L, "I am admin", "adminPassword", User.Role.ADMIN));
        userRepository.flush();
    }
}
