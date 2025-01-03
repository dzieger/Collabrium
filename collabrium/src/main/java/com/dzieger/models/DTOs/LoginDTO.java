package com.dzieger.models.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * LoginDTO
 *
 * This class is used to provide the data transfer object for the login
 * endpoint. It is used to provide the username and password to the
 * endpoint.
 *
 * @version 1.0
 */
public class LoginDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 8, max = 24)
    private String password;

    /**
     * Default constructor
     */
    public LoginDTO() {
    }

    /**
     * Constructor
     *
     * @param username the username
     * @param password the password
     */
    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * getUsername
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * setUsername
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getPassword
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setPassword
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
