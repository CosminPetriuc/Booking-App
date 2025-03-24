// `UserController` class exposes REST endpoints and uses
// `UserService` to handle requests.
package com.example.homies.demo.controller;

import com.example.homies.demo.model.dto.ChangePasswordRequest;
import com.example.homies.demo.model.dto.UserDTO;
import com.example.homies.demo.model.dto.UserSettingsDTO;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.service.UserServiceLayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/settings")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<User> updateUserSettings(@PathVariable Long userId, @RequestBody UserSettingsDTO userSettingsDTO) {
        try {
            User updatedUser = userService.updateUserSettings(userId, userSettingsDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long userId,
            @RequestBody ChangePasswordRequest request) {
        try {
            userService.changePassword(userId, request);
            return ResponseEntity.ok("Password updated successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/get_user")
    public UserDTO getUser(@RequestParam Long userID){
        return userService.getUserDTO(userID);
    }
    @GetMapping("/find_by_email")
    public UserDTO findByEmail(@RequestParam String email){
        return new UserDTO(userService.findUserByEmail(email));
    }


}
