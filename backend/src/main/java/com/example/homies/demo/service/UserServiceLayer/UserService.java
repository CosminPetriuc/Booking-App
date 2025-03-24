//`UserService` class contains the business logic of the application.
// It uses the repository to perform CRUD operations and handles
// the conversion between `User` and `UserDTO`.
package com.example.homies.demo.service.UserServiceLayer;

import com.example.homies.demo.model.dto.ChangePasswordRequest;
import com.example.homies.demo.model.dto.UserDTO;
import com.example.homies.demo.model.dto.UserSettingsDTO;
import com.example.homies.demo.model.user.User;

import java.util.List;

public interface UserService {
    User saveUser(UserDTO userDto);

    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();

    User updateUserSettings(Long userId, UserSettingsDTO userSettingsDTO);

    User findUserById(Long userID);

    UserDTO getUserDTO(Long userID);
    String getUserPassword(Long userID);

    void changePassword(Long userId, ChangePasswordRequest request);

}
