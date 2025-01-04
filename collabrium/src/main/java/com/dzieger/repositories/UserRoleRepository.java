package com.dzieger.repositories;

import com.dzieger.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * UserRoleRepository is the repository interface for managing UserRole entities.
 * It provides methods to perform CRUD operations for user-role associations,
 * which link users to their assigned roles in the system.
 *
 * @version 1.0
 */

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
}
