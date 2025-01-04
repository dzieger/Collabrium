package com.dzieger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Represents the association between a user and a role.
 * This entity links the AppUser and Role entities through many-to-one relationships.
 * It is mapped to the "user_roles" table in the database.
 *
 * @version 1.0
 */

@Schema(name = "UserRole", description = "The user role entity")
@Component
@Entity
@Table(name = "user_roles")
public class UserRole {

    /**
     * The id of the user role.
     * This is the primary key for the user role entity.
     */
    @Schema(name = "id", description = "The id of the user role", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * The user of the user role.
     * This is a required field for the user role entity.
     */
    @Schema(name = "user", description = "The user of the user role")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private AppUser appUser;

    /**
     * The role of the user role.
     * This is a required field for the user role entity.
     */
    @Schema(name = "role", description = "The role of the user role")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Role role;

    /**
     * Default constructor
     */
    public UserRole() {
    }

    /**
     * Constructor
     *
     * @param id The id of the user role
     * @param appUser The user of the user role
     * @param role The role of the user role
     */
    public UserRole(UUID id, AppUser appUser, Role role) {
        this.id = id;
        this.appUser = appUser;
        this.role = role;
    }


    /**
     * getId
     * @return the id of the user role
     */
    public UUID getId() {
        return id;
    }

    /**
     * setId
     * @param id the id of the user role
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * getUser
     * @return the user of the user role
     */
    public AppUser getUser() {
        return appUser;
    }

    /**
     * setUser
     * @param appUser the user of the user role
     */
    public void setUser(AppUser appUser) {
        this.appUser = appUser;
    }

    /**
     * getRole
     * @return the role of the user role
     */
    public Role getRole() {
        return role;
    }

    /**
     * setRole
     * @param role the role of the user role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * toString
     * @return the string representation of the user role
     */
    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}
