package com.example.homies.demo.controller;

import com.example.homies.demo.controller.UserPreferenceController;
import com.example.homies.demo.model.user.Preference;
import com.example.homies.demo.service.UserPreferenceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser // Mock an authenticated user for all tests in this class
class UserPreferenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserPreferenceService userPreferenceService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testGetAllPreferences_ReturnsPreferences() throws Exception {
        List<Preference> preferences = List.of(Preference.MUSIC, Preference.ART);
        when(userPreferenceService.getUserPreferences(anyLong())).thenReturn(preferences);

        mockMvc.perform(get("/api/users/1/preferences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("MUSIC"))
                .andExpect(jsonPath("$[1]").value("ART"));

        // Verify that the service method was called once
        verify(userPreferenceService, times(1)).getUserPreferences(1L);
    }

    @Test
    void testGetAllPreferences_UserNotFound() throws Exception {
        when(userPreferenceService.getUserPreferences(anyLong())).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/api/users/1/preferences"))
                .andExpect(status().isNotFound());

        verify(userPreferenceService, times(1)).getUserPreferences(1L);
    }

    @Test
    void testAddPreference_AddsPreferences() throws Exception {
        List<Preference> preferences = List.of(Preference.MUSIC, Preference.ART);
        when(userPreferenceService.getUserPreferences(anyLong())).thenReturn(List.of());

        mockMvc.perform(post("/api/users/1/preferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"MUSIC\", \"ART\"]"))
                .andExpect(status().isCreated());

        verify(userPreferenceService, times(1)).getUserPreferences(1L);
        verify(userPreferenceService, times(1)).addUserPreference(1L, Preference.MUSIC);
        verify(userPreferenceService, times(1)).addUserPreference(1L, Preference.ART);
    }

    @Test
    void testAddPreference_PreferenceAlreadyExists_DoesNotAddPreference() throws Exception {
        // Arrange
        List<Preference> existingPreferences = List.of(Preference.MUSIC, Preference.ART);
        List<Preference> newPreferences = List.of(Preference.MUSIC); // "MUSIC" already exists in user's preferences

        // Mock the service to return the existing preferences
        when(userPreferenceService.getUserPreferences(anyLong())).thenReturn(existingPreferences);

        // Act & Assert
        mockMvc.perform(post("/api/users/1/preferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"MUSIC\"]"))
                .andExpect(status().isCreated());

        // Verify that addUserPreference is never called for "MUSIC" because it already exists
        verify(userPreferenceService, times(1)).getUserPreferences(1L);
        verify(userPreferenceService, never()).addUserPreference(1L, Preference.MUSIC);
    }

    @Test
    void testAddPreference_UserNotFound() throws Exception {
        when(userPreferenceService.getUserPreferences(anyLong())).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(post("/api/users/1/preferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"MUSIC\"]"))
                .andExpect(status().isNotFound());

        verify(userPreferenceService, times(1)).getUserPreferences(1L);
    }

    @Test
    void testDeletePreference_DeletesPreferences() throws Exception {
        List<Preference> preferences = List.of(Preference.MUSIC, Preference.ART);

        mockMvc.perform(delete("/api/users/1/preferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"MUSIC\", \"ART\"]"))
                .andExpect(status().isNoContent());

        verify(userPreferenceService, times(1)).deleteUserPreference(1L, Preference.MUSIC);
        verify(userPreferenceService, times(1)).deleteUserPreference(1L, Preference.ART);
    }

    @Test
    void testDeletePreference_UserNotFound() throws Exception {
        when(userPreferenceService.getUserPreferences(anyLong())).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(delete("/api/users/1/preferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"MUSIC\"]"))
                .andExpect(status().isNotFound());

        verify(userPreferenceService, times(1)).getUserPreferences(1L);
    }
}
