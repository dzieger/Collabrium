package com.dzieger.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Role is the entity class for the role entity.
 */
@Schema(name = "Role", description = "The role entity")
@Component
@Entity
@Table(name = "roles")
public class Role {


    @Schema(name = "id", description = "The id of the role", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(name = "name", description = "The name of the role", example = "ROLE_USER")
    @Column(nullable = false)
    private String name;

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
