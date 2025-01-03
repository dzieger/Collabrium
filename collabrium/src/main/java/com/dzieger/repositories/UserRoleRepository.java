package com.dzieger.repositories;

import com.dzieger.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * UserRoleRepository is the repository interface for the user role entity.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
}
