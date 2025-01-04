package com.dzieger.models.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * LoginDTO
 *
 * Represents the data required for user authentication. This class serves
 * as a Data Transfer Object (DTO) for the login endpoint.
 *
 * Validation constraints ensure that the fields are properly populated.
 *
 * Example:
 * <pre>
 *     LoginDTO loginDTO = new LoginDTO("user", "password123");
 * </pre>
 *
 * @version 1.0
 */
public class LoginDTO {

    /**
     * The username for authentication.
     * Must be non-blank
     */
    @NotBlank(message = "Username cannot be blank")
    private String username;

    /**
     * The password for authentication.
     */
    @NotBlank(message = "Password cannot be blank")
    private String password;

    /**
     * Default constructor.
     */
    public LoginDTO() {
    }

    /**
     * Constructs a LoginDTO with the provided username and password.
     *
     * @param username the username
     * @param password the password
     */
    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the username.
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates the password.
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
