package com.example.homies.demo.service;

import com.example.homies.demo.model.user.Preference;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPreferenceService {

    @Autowired
    private UserRepository userRepository;

    public List<Preference> getUserPreferences(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getPreferences();
    }

    public void addUserPreference(Long userId, Preference preference) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getPreferences().add(preference);
        userRepository.save(user);
    }

    public void deleteUserPreference(Long userId, Preference preference) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getPreferences().remove(preference);
        userRepository.save(user);
    }
}
