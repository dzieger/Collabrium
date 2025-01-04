package com.dzieger.controllers;

import com.dzieger.models.DTOs.LoginDTO;
import com.dzieger.models.DTOs.TokenDTO;
import com.dzieger.models.DTOs.UserRegisterDTO;
import com.dzieger.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles all authentication-related requests:
 *  - Login
 *  - Token Refresh
 *  - Logout
 *  - User Registration
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    /**
     * Constructor for the AuthController
     * @param authService - The AuthService to handle the business logic for authentication
     */
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login to the application
     * @param credentials - The user credentials to login
     * @return - A JWT token
     */
    @Operation(
            summary = "Login to the application",
            description = "Provide Valid User credentials to login to the application. Returns a JWT token.",
            tags = {"User Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials provided")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO credentials) {
        log.debug("Endpoint *** /auth/login *** hit: Attempting to log in user with username: " + credentials.getUsername());
        return ResponseEntity.ok(authService.login(credentials));
    }

    /**
     * Refresh a JWT token
     * @param incomingTokenDTO - The JWT token to refresh
     * @return - A new JWT token
     */
    @Operation(
            summary = "Refresh a JWT token",
            description = "Provide a valid JWT token to refresh it. Returns a new JWT token.",
            tags = {"User Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed"),
            @ApiResponse(responseCode = "400", description = "Invalid token provided")
    })
    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@Valid @RequestBody TokenDTO incomingTokenDTO) {
        log.debug("Endpoint *** /auth/refresh *** hit: Attempting to refresh token");
        return ResponseEntity.ok(authService.refresh(incomingTokenDTO));
    }

    /**
     * Logout of the application
     * @param tokenDTO - The JWT token to logout
     * @return - A message indicating the logout was successful
     */
    @Operation(
            summary = "Logout of the application",
            description = "Provide a valid JWT token to logout of the application.",
            tags = {"User Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "400", description = "Invalid token provided")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody TokenDTO tokenDTO) {
        log.debug("Endpoint *** /auth/logout *** hit: Attempting to log out user");
        return ResponseEntity.ok(authService.logout(tokenDTO));
    }

    /**
     * Register a new user
     * @param userInformation - The user information to register a new user
     * @return - A message indicating the user was registered successfully
     */
    @Operation(
            summary = "Register a new user",
            tags = {"User Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Username already taken")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDTO userInformation) {
        log.debug("Endpoint *** /auth/register *** hit: Attempting to register new user with username: " + userInformation.getUsername());
        return ResponseEntity.ok(authService.register(userInformation));
    }

    /**
     * Update the password for a user
     * @param userInformation - The user information to update the password
     * @return - A message indicating the password was updated successfully
     */
    @Operation(
            summary = "Update the password for a user",
            tags = {"User Authentication"})
    @PatchMapping("/update/password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UserRegisterDTO userInformation) {
        log.debug("Endpoint *** /auth/update/password *** hit: Attempting to update password for user with username: " + userInformation.getUsername());
        return ResponseEntity.ok("Not yet implemented");
    }

    /**
     * Revoke a token
     * @param tokenDTO - The token to revoke
     * @return - A message indicating the token was revoked successfully
     */
    @Operation(
            summary = "Revoke a token",
            tags = {"User Authentication"})
    @PatchMapping("/account/token/revoke")
    public ResponseEntity<String> revokeToken(@Valid @RequestBody TokenDTO tokenDTO) {
        log.debug("Endpoint *** /auth/account/token/revoke *** hit: Attempting to revoke token");
        return ResponseEntity.ok("Not yet implemented");
    }

}
