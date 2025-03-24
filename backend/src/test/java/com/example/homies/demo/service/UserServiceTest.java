package com.example.homies.demo.service;

import com.example.homies.demo.model.dto.UserDTO;
import com.example.homies.demo.model.dto.UserSettingsDTO;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.repository.UserRepository;
import com.example.homies.demo.service.UserServiceLayer.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder; // Mocking PasswordEncoder

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;
    private UserSettingsDTO userSettingsDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");

        userSettingsDTO = new UserSettingsDTO();
        userSettingsDTO.setFirstName("Johnny");
        userSettingsDTO.setLastName("Doey");
    }

    @Test
    void testUpdateUserSettings() {
        // Mock findById to return the user
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        // Mock save to return the updated user
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Call the service method
        User updatedUser = userService.updateUserSettings(user.getUserId(), userSettingsDTO);

        // Assertions
        assertEquals(userSettingsDTO.getFirstName(), updatedUser.getFirstName());
        assertEquals(userSettingsDTO.getLastName(), updatedUser.getLastName());

        // Verify repository interactions
        verify(userRepository, times(1)).findById(user.getUserId());
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void testUpdateUserSettings_UserNotFound() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> userService.updateUserSettings(user.getUserId(), userSettingsDTO));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

}
