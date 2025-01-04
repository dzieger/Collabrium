package com.dzieger.repositories;

import com.dzieger.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * RoleRepository is the repository interface for managing Role entities.
 * It provides methods to perform CRUD operations and custom queries
 * for the Role entity.
 *
 * @version 1.0
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    /**
     * Find a role by name, ignoring case.
     *
     * @param name The name of the role to find
     * @return The role, if found
     */
    Optional<Role> findByNameIgnoreCase(String name);
}
