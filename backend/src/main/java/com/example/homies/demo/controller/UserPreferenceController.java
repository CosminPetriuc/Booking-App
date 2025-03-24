package com.example.homies.demo.controller;

import com.example.homies.demo.model.user.Preference;
import com.example.homies.demo.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/preferences")
public class UserPreferenceController {

    @Autowired
    private UserPreferenceService userPreferenceService;

    @GetMapping("/getAllPreferences")
    public ResponseEntity<List<Preference>> getAllPreferences(@PathVariable Long userId) {
        List<Preference> preferences;
        try {
            preferences = userPreferenceService.getUserPreferences(userId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(preferences);
    
    }

   @PostMapping("/addPreference")
    public ResponseEntity<Void> addPreference(@PathVariable Long userId, @RequestBody List<Preference> preferences) {
       List<Preference> existingPreferences;

       try {
           existingPreferences = userPreferenceService.getUserPreferences(userId);
       } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }
       if (preferences == null || preferences.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
       }
       else {
            // Add each new preference that does not already exist
            for (Preference preference : preferences) {
                if (!existingPreferences.contains(preference)) {
                    userPreferenceService.addUserPreference(userId, preference);
                }
            }
       }
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("deletePreference")
    public ResponseEntity<Void> deletePreference(@PathVariable Long userId, @RequestBody List<Preference> preferences) {
        List<Preference> existingPreferences;

        try {
            existingPreferences = userPreferenceService.getUserPreferences(userId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (preferences == null || preferences.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else {
            // Delete each preference for the user
            for (Preference preference : preferences) {
                userPreferenceService.deleteUserPreference(userId, preference);
            }
        }
        return ResponseEntity.noContent().build();
    }
}
