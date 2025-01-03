package com.dzieger.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles exceptions globally across the application.
 * <p>
 * This class centralizes exception handling for better consistency and maintainability.
 * Exceptions are mapped to appropriate HTTP status codes and error messages.
 * </p>
 *
 * <p>Key Features:</p>
 * <ul>
 *     <li>Handles custom exceptions like InvalidTokenException and TokenExpiredException.</li>
 *     <li>Maps Spring Security exceptions like UsernameNotFoundException.</li>
 *     <li>Provides a fallback handler for uncaught exceptions.</li>
 * </ul>
 *
 * @version 1.0
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Default constructor
     */
    public GlobalExceptionHandler() {
        // Default constructor
    }

    /**
     * handleInvalidTokenException is used to handle InvalidTokenException exceptions.
     * <p>
     *     InvalidTokenException is thrown when a token is found to be invalid by the JwtUtil.
     *     This can happen if the token is malformed or if the signature is invalid.
     *     The exception is mapped to a 401 Unauthorized response.
     * </p>
     *
     * @param e The InvalidTokenException
     * @return A ResponseEntity with the error message
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTokenException(InvalidTokenException e) {
        logger.warn("Invalid token: {}", e.getMessage());
        return createResponse(HttpStatus.UNAUTHORIZED, "Invalid token", e.getMessage());
    }

    /**
     * handleTokenExpiredException is used to handle TokenExpiredException exceptions.
     * <p>
     *     TokenExpiredException is thrown when a token is found to be expired by the Jwt.
     *     This can happen if the token is older than the expiration time.
     *     The exception is mapped to a 401 Unauthorized response.
     * </p>
     *
     * @param e The TokenExpiredException
     * @return A ResponseEntity with the error message
     */
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Map<String, String>> handleTokenExpiredException(TokenExpiredException e) {
        logger.warn("Token expired: {}", e.getMessage());
        return createResponse(HttpStatus.UNAUTHORIZED, "Token expired", e.getMessage());
    }

    /**
     * handleException is used to handle all other exceptions.
     * <p>
     *     This method is a fallback handler for any uncaught exceptions.
     *     It maps all other exceptions to a 500 Internal Server Error response.
     *     The error message is included in the response body.
     * </p>
     *
     * @param e The Exception
     * @return A ResponseEntity with the error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        logger.error("Unexpected error occurred: {}", e.getMessage(), e);
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage());
    }

    /**
     * handleIllegalArgumentException is used to handle IllegalArgumentException exceptions.
     * <p>
     *     IllegalArgumentException is thrown when an invalid argument is provided to a method.
     *     This can happen if a required parameter is missing or if an invalid value is provided.
     *     The exception is mapped to a 400 Bad Request response.
     * </p>
     *
     * @param e The IllegalArgumentException
     * @return A ResponseEntity with the error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.warn("Bad request: {}", e.getMessage());
        return createResponse(HttpStatus.BAD_REQUEST, "Invalid argument", e.getMessage());
    }

    /**
     * handleUsernameNotFoundException is used to handle UsernameNotFoundException exceptions.
     * <p>
     *     This method is used to handle Spring Security UsernameNotFoundException exceptions.
     *     UsernameNotFoundException is thrown when a user is not found in the database.
     *     The exception is mapped to a 401 Unauthorized response.
     * </p>
     *
     * @param e The UsernameNotFoundException
     * @return A ResponseEntity with the error message
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        logger.warn("User not found: {}", e.getMessage());
        return createResponse(HttpStatus.UNAUTHORIZED, "User not found", e.getMessage());
    }

    /**
     * handleUserNotFoundException is used to handle UserNotFoundException exceptions.
     * <p>
     *     This method is used to handle custom UserNotFoundException exceptions for general queries other than authentication.
     *     UserNotFoundException is thrown when a user is not found in the database.
     *     The exception is mapped to a 404 Not Found response.
     * </p>
     *
     * @param e The UserNotFoundException
     * @return A ResponseEntity with the error message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException e) {
        logger.warn("User not found: {}", e.getMessage());
        return createResponse(HttpStatus.NOT_FOUND, "User not found", e.getMessage());
    }

    /**
     * createResponse is a helper method to create a response entity with an error message.
     *
     * @param status The HTTP status code
     * @param error The error message
     * @param message The error details
     * @return A ResponseEntity with the error message
     */
    private ResponseEntity<Map<String, String>> createResponse(HttpStatus status, String error, String message) {
        return ResponseEntity.status(status).body(Map.of("error", error, "message", message));
    }

}
