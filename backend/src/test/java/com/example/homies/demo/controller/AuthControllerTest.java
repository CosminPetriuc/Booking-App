//package com.example.homies.demo.controller;
//
//import com.example.homies.demo.model.dto.UserDTO;
//import com.example.homies.demo.model.user.User;
//import com.example.homies.demo.repository.UserRepository;
//import com.example.homies.demo.service.UserServiceLayer.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AuthControllerTest {
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private AuthController authController;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testHome() {
//        // When
//        ResponseEntity<String> response = authController.home();
//
//        // Then
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Welcome to the Auth API", response.getBody());
//    }
//
//    @Test
//    void testShowRegistrationForm() {
//        // When
//        ResponseEntity<UserDTO> response = authController.showRegistrationForm();
//
//        // Then
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//    }
//
//    @Test
//    void testRegistrationSuccess() {
//        // Given
//        UserDTO userDTO = new UserDTO();
//        userDTO.setEmail("test@example.com");
//
//        User createdUser = new User();
//        createdUser.setUserId(1L); // Assuming `User` has a `setUserId` method
//
//        when(userService.findUserByEmail(userDTO.getEmail())).thenReturn(null);
//        when(userService.saveUser(userDTO)).thenReturn(createdUser);
//
//        // When
//        ResponseEntity<?> response = authController.registration(userDTO);
//
//        // Then
//        verify(userService, times(1)).saveUser(userDTO);
//        assertEquals(200, response.getStatusCodeValue());
//
//        // Validate the response body
//        Map<String, Object> expectedResponse = new HashMap<>();
//        expectedResponse.put("message", "Login successful");
//        expectedResponse.put("userId", 1L);
//
//        assertEquals(expectedResponse, response.getBody());
//    }
//
//
//    @Test
//    void testRegistrationEmailAlreadyExists() {
//        // Given
//        UserDTO userDTO = new UserDTO();
//        userDTO.setEmail("existing@example.com");
//        User existingUser = new User();
//        existingUser.setEmail("existing@example.com");
//
//        when(userService.findUserByEmail(userDTO.getEmail())).thenReturn(existingUser);
//
//        // When
//        ResponseEntity<?> response = authController.registration(userDTO);
//
//        // Then
//        verify(userService, never()).saveUser(any(UserDTO.class));
//        assertEquals(400, response.getStatusCodeValue());
//        assertEquals("There is already an account registered with the same email.", response.getBody());
//    }
//
//    @Test
//    void testUsersList() {
//        // Given
//        List<UserDTO> usersList = new ArrayList<>();
//        usersList.add(new UserDTO());
//        when(userService.findAllUsers()).thenReturn(usersList);
//
//        // When
//        ResponseEntity<List<UserDTO>> response = authController.users();
//
//        // Then
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals(1, response.getBody().size());
//        verify(userService, times(1)).findAllUsers();
//    }
//
//    @Test
//    void testLoginSuccess() {
//        // Given
//        UserDTO userDTO = new UserDTO();
//        userDTO.setEmail("test@example.com");
//        userDTO.setPassword("password");
//
//        User user = new User();
//        user.setEmail("test@example.com");
//        user.setPassword("$2a$10$7QJ8K1QJ8K1QJ8K1QJ8K1O");
//
//        when(userService.findUserByEmail(userDTO.getEmail())).thenReturn(user);
//        when(passwordEncoder.matches(userDTO.getPassword(), user.getPassword())).thenReturn(true);
//
//        // When
//        ResponseEntity<?> response = authController.login(userDTO,"password");
//
//        // Then
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Login successful", response.getBody());
//    }
//
//    @Test
//    void testLoginInvalidEmail() {
//        // Given
//        UserDTO userDTO = new UserDTO();
//        userDTO.setEmail("invalid@example.com");
//        userDTO.setPassword("password");
//
//        when(userService.findUserByEmail(userDTO.getEmail())).thenReturn(null);
//
//        // When
//        ResponseEntity<?> response = authController.login(userDTO,"password");
//
//        // Then
//        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
//        assertEquals("Invalid email or password", response.getBody());
//    }
//
//    @Test
//    void testLoginInvalidPassword() {
//        // Given
//        UserDTO userDTO = new UserDTO();
//        userDTO.setEmail("test@example.com");
//        userDTO.setPassword("invalidpassword");
//
//        User user = new User();
//        user.setEmail("test@example.com");
//        user.setPassword("$2a$10$7QJ8K1QJ8K1QJ8K1QJ8K1O");
//
//        when(userService.findUserByEmail(userDTO.getEmail())).thenReturn(user);
//        when(passwordEncoder.matches(userDTO.getPassword(), user.getPassword())).thenReturn(false);
//
//        // When
//        ResponseEntity<?> response = authController.login(userDTO, userDTO.getPassword());
//
//        // Then
//        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
//        assertEquals("Invalid email or password", response.getBody());
//    }
//
//}
