package com.example.homies.demo.roleTesting;


import com.example.homies.demo.model.user.Role;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.repository.RoleRepository;
import com.example.homies.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.assertj.core.api.Assertions.assertThat;
@WithMockUser
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleTests {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private User user = new User();

    private Role role;

    @BeforeEach
    void setUp(){
        // Create and save the user
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setDescription("Test user");
        user.setPassword("securePassword");

        // Save user first to ensure user ID is generated before role
        user = userRepository.save(user);

        // Create and save the role
        role = new Role();
        role.setRoleName("User_client");

        // Associate the role with the user
        role.setUser(user);  // Set the user for the role

        // Save the role, ensuring the user_id is populated in the role
        role = roleRepository.save(role);
    }
    @Test
    void roleSetUp(){
        assertThat(roleRepository.count()).isEqualTo(2);
        assertThat(roleRepository.getReferenceById(role.getRoleId())).isEqualTo(role);
    }

}
