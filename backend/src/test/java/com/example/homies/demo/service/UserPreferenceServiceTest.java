package com.example.homies.demo.service;


import com.example.homies.demo.model.user.Preference;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserPreferenceServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserPreferenceService userPreferenceService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setPreferences(new ArrayList<>(List.of(Preference.PET_FRIENDLY))); // Now mutable

    }

    @Test
    void testGetUserPreferences_UserExists_ReturnsPreferences() {
        // Arrange
       when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        List<Preference> preferences = userPreferenceService.getUserPreferences(1L);

        // Assert
        assertNotNull(preferences);
        assertEquals(1, preferences.size());
        assertTrue(preferences.contains(Preference.PET_FRIENDLY));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserPreferences_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userPreferenceService.getUserPreferences(1L);
        });
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testAddUserPreference_UserExists_AddsPreference() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        userPreferenceService.addUserPreference(1L, Preference.MUSIC);

        // Assert
        assertTrue(user.getPreferences().contains(Preference.MUSIC));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAddUserPreference_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userPreferenceService.addUserPreference(1L, Preference.MUSIC);
        });
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUserPreference_UserExists_RemovesPreference() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        userPreferenceService.deleteUserPreference(1L, Preference.PET_FRIENDLY);

        // Assert
        assertFalse(user.getPreferences().contains(Preference.PET_FRIENDLY));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUserPreference_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userPreferenceService.deleteUserPreference(1L, Preference.PET_FRIENDLY);
        });
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }
}