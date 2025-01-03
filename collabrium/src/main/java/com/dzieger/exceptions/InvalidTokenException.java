package com.dzieger.exceptions;

/**
 * Exception thrown when an invalid token is encountered during authentication or authorization.
 * <p>
 * This exception is typically used in scenarios where:
 * - A token is malformed or contains invalid claims.
 * - The token's signature cannot be verified.
 * </p>
 *
 * @version 1.0
 */

public class InvalidTokenException extends RuntimeException {

    /**
     * Constructs a new InvalidTokenException with the specified message.
     * <p>
     * This constructor is typically used when the token is known to be invalid,
     * but no additional cause is available.
     * </p>
     *
     * @param message The exception message
     */

    public InvalidTokenException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidTokenException with the specified message and cause.
     *
     * This constructor is typically used when the token is known to
     * be invalid and an additional cause is available.
     *
     * @param message The exception message
     * @param cause The exception cause
     */
    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new InvalidTokenException with a default message.
     */
    public InvalidTokenException() {
        super("Invalid token");
    }

    /**
     * Returns a string representation of the InvalidTokenException.
     *
     * @return A string representation of the InvalidTokenException
     */
    @Override
    public String toString() {
        return "InvalidTokenException{message='" + getMessage() + "'}";
    }
}
