package com.dzieger.repositories;

import com.dzieger.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository is the repository interface for managing AppUser entities.
 * It provides methods to perform CRUD operations and custom queries
 * for user data, such as finding users by username or email.
 *
 * @version 1.0
 */

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    /**
     * Find a user by username.
     *
     * @param username The username of the user to find
     * @return The user, if found
     */
    Optional<AppUser> findByUsername(String username);

    /**
     * Find a user by email.
     *
     * @param email The email of the user to find
     * @return The user, if found
     */
    Optional<AppUser> findByEmail(String email);

}
