package com.example.homies.demo.controller;

import com.example.homies.demo.model.dto.UserDTO;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.service.UserServiceLayer.UserService;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth") // Set a base path for the API
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Home endpoint
    @GetMapping("/index")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Auth API");
    }

    // Get initial data for the registration form
    @GetMapping("/register")
    public ResponseEntity<UserDTO> showRegistrationForm() {
        UserDTO userDTO = new UserDTO(); // Create an empty DTO
        return ResponseEntity.ok(userDTO); // Return it as JSON
    }

    // Handle user registration
    @PostMapping("/register/save")
    public ResponseEntity<?> registration(@Valid @RequestBody UserDTO userDTO) {
        // Check if the user already exists
        User existingUser = userService.findUserByEmail(userDTO.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("There is already an account registered with the same email.");
        }

        // Save the user
        User createdUser = userService.saveUser(userDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("userId", createdUser.getUserId()); // Assuming the User entity has a `getId()` method

        // Return the response with the user ID
        return ResponseEntity.ok(response);
    }

    // Retrieve all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> users() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users); // Return the list of users as JSON
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        UserDTO userDTO = new UserDTO(userService.findUserByEmail(email));
        userDTO.setPassword(password);
        User user = userService.findUserByEmail(userDTO.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password: Searched user returned null");
        }
        user.setPassword(userService.getUserPassword(user.getUserId()));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        // Return JSON instead of plain text
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("userId", user.getUserId());
        return ResponseEntity.ok(response);
    }

}
