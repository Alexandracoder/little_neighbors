package com.littleneighbors.features.user.service;

import com.littleneighbors.LittleNeighborsApplication;
import com.littleneighbors.features.user.dto.UserRequest;
import com.littleneighbors.features.user.dto.UserResponse;
import com.littleneighbors.features.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LittleNeighborsApplication.class)
@ActiveProfiles("test-h2")
@Transactional
public class UserServiceIntegrationH2Test {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setEmail("picopico@prueba.com");
        userRequest.setPassword("password123");
    }

    @Test
    void createUserAndGetUser_success() {
        UserResponse created = userService.createUser(userRequest);
        assertNotNull(created.getId());
        assertEquals(userRequest.getEmail(), created.getEmail());

        UserResponse fetched = userService.getUserById(created.getId());
        assertEquals(created.getEmail(), fetched.getEmail());
        assertEquals(created.getId(), fetched.getId());
    }

    @Test
    void updateUser_success() {
        UserResponse created = userService.createUser(userRequest);

        UserRequest updateRequest = new UserRequest();
        updateRequest.setEmail("nuevo@correo.com");
        updateRequest.setPassword("newPassword");

        UserResponse updated = userService.updateUser(created.getId(), updateRequest);

        assertEquals(updateRequest.getEmail(), updated.getEmail());
    }

    @Test
    void deleteUser_success() {
        UserResponse created = userService.createUser(userRequest);
        Long id = created.getId();

        userService.deleteUser(id);

        assertFalse(userRepository.findById(id).isPresent());
    }
}

