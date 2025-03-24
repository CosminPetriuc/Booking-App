package com.example.homies.demo.service.UserServiceLayer;

import com.example.homies.demo.model.dto.ChangePasswordRequest;
import com.example.homies.demo.model.dto.UserDTO;
import com.example.homies.demo.model.dto.UserSettingsDTO;
import com.example.homies.demo.model.user.Role;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.repository.RoleRepository;
import com.example.homies.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(UserDTO userDto) {
        // Create a new User entity
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Create or find the Role
        Role role = roleRepository.findByroleName("ROLE_ADMIN");
        if (role == null) {
            role = new Role();
            role.setRoleName("ROLE_ADMIN");
        }

        // Set the user in the Role (establish the relationship)
        role.setUser(user);

        // Add the Role to the User's list of roles
        user.setRoles(List.of(role));

        // Save the User (cascading will save the Role as well)
        return userRepository.save(user);
    }



    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByemail(email);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)//changed lambda with this::mapToUserDto
                .collect(Collectors.toList());
    }

    private UserDTO mapToUserDto(User user){
        UserDTO userDto = new UserDTO();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
    private Role checkRoleExist(){
        Role role = new Role();
        role.setRoleName("ROLE_USER");
        return roleRepository.save(role);
    }

    public User updateUserSettings(Long userId, UserSettingsDTO userSettingsDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if(userSettingsDTO.getFirstName() != null) {
            user.setFirstName(userSettingsDTO.getFirstName());
        }

        if(userSettingsDTO.getLastName() != null) {
            user.setLastName(userSettingsDTO.getLastName());
        }

        if(userSettingsDTO.getDescription() != null) {
            user.setDescription(userSettingsDTO.getDescription());
        }

        return userRepository.save(user);
    }

    public User findUserById(Long userID){
        return userRepository.getReferenceById(userID);
    }
    public UserDTO getUserDTO(Long userID){

        User user = userRepository.getReferenceById(userID);

        return new UserDTO(user);
    }

    @Override
    public String getUserPassword(Long userID) {
        Optional<User> user;
        try{
            user = userRepository.findById(userID);

        }catch (Exception e){
            return "USER NOT FOUND!";
        }
        return user.get().getPassword();
    }

    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify the current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Check if new password and confirm password match
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        // Encrypt the new password and update the user
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


}
