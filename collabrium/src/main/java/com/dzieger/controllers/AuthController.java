package com.dzieger.controllers;

import com.dzieger.models.DTOs.LoginDTO;
import com.dzieger.models.DTOs.TokenDTO;
import com.dzieger.models.DTOs.UserRegisterDTO;
import com.dzieger.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ************************ Authentication Controller ************************
 *
 * This controller is responsible for handling all authentication requests such as:
 *  - Login
 *  - Refreshing a token
 *  - Logging out
 *  - Registering a new user
 *
 *  The controller uses the AuthService to handle the business logic for these requests.
 * @see com.dzieger.services.AuthService
 *
 * ****************************************************************************
 *
 * Endpoints:
 * - POST /auth/login
 *    - Login to the application
 *    - Provide user credentials to login to the application. Returns a JWT token.
 *    - Request Body: {String username, String password}
 *
 *    - If successful:
 *      - Returns a ResponseEntity<TokenDTO>
 *          - TokenDTO: {String token}
 *          - HttpStatus: 200 OK
 *
 *    - If invalid credentials:
 *      - HttpStatus: 400 BAD REQUEST
 *
 * - POST /auth/refresh
 *  - Refresh a JWT token
 *  - Provide a valid JWT token to refresh it. Returns a new JWT token.
 *  - Request Body: {String token}
 *
 *  - If successful:
 *      - Returns a ResponseEntity<TokenDTO>
 *          - TokenDTO: {String token}
 *          - HttpStatus: 200 OK
 *
 *  - If invalid token:
 *    - HttpStatus: 400 BAD REQUEST
 *
 * - POST /auth/logout
 *  - Logout of the application
 *  - Provide a valid JWT token to logout of the application.
 *  - Request Body: {String token}
 *
 *  - If successful:
 *     - Returns a ResponseEntity<String>
 *         - HttpStatus: 200 OK
 *         - Body: "Logout successful"
 *
 *  - If invalid token:
 *      - HttpStatus: 400 BAD REQUEST
 *
 * - POST /auth/register
 *  - Register a new user
 *  - Provide user information to register a new user.
 *  - Request Body: RegisterDTO
 *      - RegisterDTO: {String username, String password, String email, String firstName, String lastName, String phoneNumber}
 *
 *      - If successful:
 *      - Returns a ResponseEntity<String>
 *          - HttpStatus: 200 OK
 *          - Body: "User registered successfully"
 *
 *      - If username already taken:
 *      - HttpStatus: 400 BAD REQUEST
 *
 *      - If invalid email:
 *      - HttpStatus: 400 BAD REQUEST
 *
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Login to the application",
            description = "Provide user credentials to login to the application. Returns a JWT token.",
            tags = {"User Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials provided")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO credentials) {
        return ResponseEntity.ok(authService.login(credentials));
    }

    @Operation(
            summary = "Refresh a JWT token",
            description = "Provide a valid JWT token to refresh it. Returns a new JWT token.",
            tags = {"User Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed"),
            @ApiResponse(responseCode = "400", description = "Invalid token provided")
    })
    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@RequestBody TokenDTO incomingTokenDTO) {
        return ResponseEntity.ok(authService.refresh(incomingTokenDTO));
    }

    @Operation(
            summary = "Logout of the application",
            description = "Provide a valid JWT token to logout of the application.",
            tags = {"User Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "400", description = "Invalid token provided")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody TokenDTO tokenDTO) {
        return ResponseEntity.ok(authService.logout(tokenDTO));
    }

    @Operation(
            summary = "Register a new user",
            description = "Provide user information to register a new user.",
            tags = {"User Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Username already taken")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userInformation) {
        return ResponseEntity.ok(authService.register(userInformation));
    }

}
