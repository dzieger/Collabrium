package com.dzieger.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Represents a role in the system.
 * Roles define sets of permissions and are associated with users through the UserRole entity.
 * This entity is mapped to the "roles" table in the database.
 *
 * @version 1.0
 */

@Schema(name = "Role", description = "The role entity")
@Component
@Entity
@Table(name = "roles")
public class Role {

    /**
     * The id of the role.
     * This is the primary key for the role entity.
     */
    @Schema(name = "id", description = "The id of the role", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * The name of the role.
     * This is a required field for the role entity and must be unique.
     */
    @Schema(name = "name", description = "The name of the role", example = "USER")
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * The user roles associated with the role.
     * This is a list of UserRole entities that are associated with the role.
     */
    @Schema(name = "userRoles", description = "The user roles of the role")
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<UserRole> userRoles = new ArrayList<>();


    /**
     * Default constructor
     */
    public Role() {
    }

    /**
     * Constructor
     *
     * @param name The name of the role
     */
    public Role(String name) {
        this.name = name;
    }


    /**
     * getId
     * @return the id of the role
     */
    public UUID getId() {
        return id;
    }

    /**
     * setId
     * @param id the id of the role
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * getName
     * @return the name of the role
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * @param name the name of the role
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getUserRoles
     * @return the user roles of the role
     */
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    /**
     * setUserRoles
     * @param userRoles the user roles of the role
     */
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
