package com.example.homies.demo.DTOs_testing;

import com.example.homies.demo.model.dto.UserDTO;
import com.example.homies.demo.model.user.Preference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

public class userDtoTesting {

    private UserDTO validUserDTO;
    private UserDTO invalidUserDTO;

    @BeforeEach
    public void setup() {
        // Create a valid UserDTO object
        validUserDTO = new UserDTO(1L, "John", "Doe", "johndoe@example.com","noDescription",  List.of(Preference.PET_FRIENDLY, Preference.MUSIC));

        // Create an invalid UserDTO object (missing first name, last name)
        invalidUserDTO = new UserDTO(1L, "", "", "johndoe@example.com","noDescription", null);
    }

    @Test
    public void testValidUserDTO() {
        // Assert that the valid UserDTO has the correct values
        assertThat(validUserDTO).isNotNull();
        assertThat(validUserDTO.getId()).isEqualTo(1L);
        assertThat(validUserDTO.getFirstName()).isEqualTo("John");
        assertThat(validUserDTO.getLastName()).isEqualTo("Doe");
        assertThat(validUserDTO.getEmail()).isEqualTo("johndoe@example.com");
        //assertThat(validUserDTO.getPassword()).isEqualTo("password123");
        assertThat(validUserDTO.getPreferences()).isNotNull();
        assertThat(validUserDTO.getPreferences()).isNotEmpty();
    }

    @Test
    public void testInvalidUserDTO() {
        // Assert that the invalid UserDTO has the wrong values (empty first name and last name)
        assertThat(invalidUserDTO).isNotNull();
        assertThat(invalidUserDTO.getFirstName()).isEmpty();
        assertThat(invalidUserDTO.getLastName()).isEmpty();
        assertThat(invalidUserDTO.getEmail()).isEqualTo("johndoe@example.com");
        //assertThat(invalidUserDTO.getPassword()).isEqualTo("password123");
        assertThat(invalidUserDTO.getPreferences()).isNull();
    }

    @Test
    public void testSettersAndGetters() {
        // Create a new UserDTO and set fields using setters
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Jane");
        userDTO.setLastName("Doe");
        userDTO.setEmail("janedoe@example.com");
        // Assert that the fields are set correctly
        assertThat(userDTO.getFirstName()).isEqualTo("Jane");
        assertThat(userDTO.getLastName()).isEqualTo("Doe");
        assertThat(userDTO.getEmail()).isEqualTo("janedoe@example.com");
    }

    @Test
    public void testConstructor() {
        // Create a UserDTO object using the constructor
        UserDTO userDTO = new UserDTO(2L, "Alice", "Smith", "alicesmith@example.com", "password456", List.of(Preference.PET_FRIENDLY, Preference.MUSIC));

        // Assert that the constructor sets the fields correctly
        assertThat(userDTO.getId()).isEqualTo(2L);
        assertThat(userDTO.getFirstName()).isEqualTo("Alice");
        assertThat(userDTO.getLastName()).isEqualTo("Smith");
        assertThat(userDTO.getEmail()).isEqualTo("alicesmith@example.com");
        assertThat(userDTO.getPreferences()).isNotNull();
        assertThat(userDTO.getPreferences()).isNotEmpty();
    }
}
