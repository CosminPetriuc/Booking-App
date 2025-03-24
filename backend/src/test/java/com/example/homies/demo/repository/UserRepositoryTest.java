package com.example.homies.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // This annotation lets Spring Boot know that this is a test for JPA repository
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // This annotation lets Spring Boot know that this is a test for H2 database
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
}
