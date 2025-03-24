// `UserDTO` is a simple class used to transfer data.
// It contains only the fields you want to expose in your API.
package com.example.homies.demo.model.dto;

import com.example.homies.demo.model.user.Preference;
import com.example.homies.demo.model.user.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    private String password;

    private List<Preference> preferences;
    public UserDTO(User user){
        this.setId(user.getUserId());
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPreferences(user.getPreferences());
    }
}