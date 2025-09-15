package com.littleneighbors.features.user.service;

import com.littleneighbors.features.user.dto.UserRequest;
import com.littleneighbors.features.user.dto.UserResponse;
import com.littleneighbors.features.user.mapper.UserMapper;
import com.littleneighbors.features.user.model.User;
import com.littleneighbors.features.user.repository.UserRepository;
import com.littleneighbors.features.user.service.UserService;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test-h2") // Perfil para H2
@Transactional
public class UserServiceIntegrationH2Test {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    void createUserAndGetUser_success() {
        UserRequest request = new UserRequest();
        request.setEmail("integration@h2.com");
        request.setPassword("password123");

        UserResponse response = userService.createUser(request);
        assertNotNull(response.getId());
        assertEquals("integration@h2.com", response.getEmail());

        User persisted = userRepository.findById(response.getId()).orElseThrow();
        assertEquals("integration@h2.com", persisted.getEmail());

        UserResponse fetched = userService.getUserById(response.getId());
        assertEquals(response.getEmail(), fetched.getEmail());
    }

    @Test
    void updateUser_success() {
        UserRequest request = new UserRequest();
        request.setEmail("update@h2.com");
        request.setPassword("password123");

        UserResponse created = userService.createUser(request);

        UserRequest updateRequest = new UserRequest();
        updateRequest.setEmail("updated@h2.com");
        updateRequest.setPassword("newpass123");

        UserResponse updated = userService.updateUser(created.getId(), updateRequest);
        assertEquals("updated@h2.com", updated.getEmail());
    }

    @Test
    void deleteUser_success() {
        UserRequest request = new UserRequest();
        request.setEmail("delete@h2.com");
        request.setPassword("password123");

        UserResponse created = userService.createUser(request);
        Long id = created.getId();

        userService.deleteUser(id);

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(id));
    }
}
