package com.example.homies.demo.controller;

import com.example.homies.demo.model.dto.UserSettingsDTO;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.service.UserServiceLayer.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateUserSettings_Success() {
        // Arrange
        Long userId = 1L;
        UserSettingsDTO userSettingsDTO = new UserSettingsDTO();
        User updatedUser = new User();
        updatedUser.setUserId(userId);

        when(userService.updateUserSettings(userId, userSettingsDTO)).thenReturn(updatedUser);

        // Act
        ResponseEntity<User> response = userController.updateUserSettings(userId, userSettingsDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedUser, response.getBody());
        verify(userService, times(1)).updateUserSettings(userId, userSettingsDTO);
    }

    @Test
    public void testUpdateUserSettings_UserNotFound() {
        // Arrange
        Long userId = 1L;
        UserSettingsDTO userSettingsDTO = new UserSettingsDTO();

        when(userService.updateUserSettings(userId, userSettingsDTO)).thenThrow(new RuntimeException("User not found"));

        // Act
        ResponseEntity<User> response = userController.updateUserSettings(userId, userSettingsDTO);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(userService, times(1)).updateUserSettings(userId, userSettingsDTO);
    }
}
