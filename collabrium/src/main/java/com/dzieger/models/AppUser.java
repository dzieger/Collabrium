package com.dzieger.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a user in the system.
 * Each user has an associated list of roles and a token version for token invalidation.
 * This entity is mapped to the "users" table in the database.
 *
 * @version 1.0
 */

@Schema(name = "AppUser", description = "The user entity")
@Component
@Entity
@Table(name = "users")
public class AppUser {


    /**
     * The id of the user.
     * This is the primary key for the user entity.
     */
    @Schema(name = "id", description = "The id of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * The username of the user.
     * This is a required field for the user entity.
     */
    @Schema(name = "username", description = "The username of the user", example = "username")
    @Column(nullable = false)
    private String username;

    /**
     * The password of the user.
     * This is a required field for the user entity.
     */
    @Schema(name = "password", description = "The password of the user", example = "password")
    @Column(nullable = false)
    private String password;

    /**
     * The email of the user.
     * This is a required field for the user entity.
     */
    @Schema(name = "email", description = "The email of the user", example = "email@email.com")
    @Column(nullable = false)
    private String email;

    /**
     * The first name of the user.
     * This is a required field for the user entity.
     */
    @Schema(name = "firstName", description = "The first name of the user", example = "firstName")
    @Column(nullable = false)
    private String firstName;

    /**
     * The last name of the user.
     * This is a required field for the user entity.
     */
    @Schema(name = "lastName", description = "The last name of the user", example = "lastName")
    @Column(nullable = false)
    private String lastName;

    /**
     * The roles of the user.
     * This is a list of UserRole entities that are associated with the user.
     */
    @Schema(name = "roles", description = "The roles of the user")
    @OneToMany(mappedBy = "appUser", fetch = FetchType.EAGER)
    private List<UserRole> roles = new ArrayList<>();

    /**
     * The token version of the user.
     * This field is used to invalidate tokens when the user's roles change.
     */
    @Schema(name = "tokenVersion", description = "The token version of the user", example = "1")
    @Column(nullable = false)
    @Version
    private int tokenVersion;

    /**
     * Default constructor
     */
    public AppUser() {
    }

    /**
     * Constructor
     *
     * @param id The id of the user
     * @param username The username of the user
     * @param password The password of the user
     * @param email The email of the user
     * @param firstName The first name of the user
     * @param lastName The last name of the user
     * @param tokenVersion The token version of the user
     */
    public AppUser(UUID id, String username, String password, String email, String firstName, String lastName, int tokenVersion) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tokenVersion = tokenVersion;
    }

    /**
     * getId
     * @return the id of the user
     */
    public UUID getId() {
        return id;
    }

    /**
     * setId
     * @param id the id of the user
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * getUsername
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * setUsername
     * @param username the username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getPassword
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * setPassword
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getEmail
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * setEmail
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getFirstName
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setFirstName
     * @param firstName the first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getLastName
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setLastName
     * @param lastName the last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * getRoles
     * @return the roles of the user
     */
    public List<UserRole> getRoles() {
        return roles;
    }

    /**
     * setRoles
     * @param roles the roles of the user
     */
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    /**
     * getTokenVersion
     * @return the token version of the user
     */
    public int getTokenVersion() {
        return tokenVersion;
    }

    /**
     * setTokenVersion
     * @param tokenVersion the token version of the user
     */
    public void setTokenVersion(int tokenVersion) {
        this.tokenVersion = tokenVersion;
    }

    /**
     * toString
     * @return the string representation of the user
     */
    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\''+
                '}';
    }
}
