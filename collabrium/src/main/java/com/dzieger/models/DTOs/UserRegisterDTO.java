package com.dzieger.models.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * UserRegisterDTO
 *
 * This class is used to provide the data transfer object for the user
 * registration endpoint. It is used to provide the user registration
 * information to the endpoint.
 *
 * Example:
 * <pre>
 *     UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user", "password123", "email@email.com", "John", "Doe");
 * </pre>
 *
 * @version 1.0
 */
public class UserRegisterDTO {

    /**
     * The username for registration.
     * Must be non-blank
     */
    @NotBlank
    private String username;

    /**
     * The password for registration.
     */
    @NotBlank
    private String password;

    /**
     * The email for registration.
     */
    @NotBlank
    @Email
    private String email;

    /**
     * The first name for registration.
     */
    @NotBlank
    private String firstName;

    /**
     * The last name for registration.
     */
    @NotBlank
    private String lastName;

    /**
     * The phone number for registration.
     * Optional
     */
    private String phoneNumber;

    /**
     * Default constructor
     */
    public UserRegisterDTO() {
    }

    /**
     * Constructor
     *
     * @param username the username
     * @param password the password
     * @param email the email
     * @param firstName the first name
     * @param lastName the last name
     */
    public UserRegisterDTO(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
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

    /**
     * getEmail
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setEmail
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getFirstName
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setFirstName
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getLastName
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setLastName
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * getPhoneNumber
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * setPhoneNumber
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
