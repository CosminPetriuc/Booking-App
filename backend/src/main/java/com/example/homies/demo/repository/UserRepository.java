//  `UserRepository` is an interface for performing database operations.
package com.example.homies.demo.repository;

import com.example.homies.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// `UserRepository` extends the `JpaRepository` interface, which provides CRUD operations.
public interface UserRepository extends JpaRepository<User, Long> {
    // The `findByEmail` method is used to find a user by email.
    User findByemail(String email);

}
