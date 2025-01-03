package com.dzieger.exceptions;

/**
 * Exception thrown when a user is not found during database or authentication operations.
 * <p>
 * This exception is typically used in scenarios such as:
 * - A username lookup failing due to the user not existing in the database.
 * - A user query returning no results.
 * </p>
 *
 * @version 1.0
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified message.
     *
     * @param message The exception message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserNotFoundException with the specified message and cause.
     *
     * @param message The exception message
     * @param cause   The exception cause
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UserNotFoundException with a default message.
     */
    public UserNotFoundException() {
        super("User not found");
    }

    /**
     * Returns a string representation of the UserNotFoundException.
     *
     * @return A string representation of the UserNotFoundException
     */
    @Override
    public String toString() {
        return "UserNotFoundException{message='" + getMessage() + "'}";
    }
}
