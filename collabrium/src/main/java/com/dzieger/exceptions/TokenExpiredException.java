package com.dzieger.exceptions;

/**
 * Exception thrown when a token is expired during authentication or authorization.
 * <p>
 * This exception is typically used in scenarios such as:
 * - A token being older than the allowed expiration time.
 * - A token that has been invalidated due to inactivity.
 * </p>
 *
 * @version 1.0
 */

public class TokenExpiredException extends RuntimeException {

    /**
     * Constructs a new TokenExpiredException with the specified message.
     *
     * @param message The exception message
     */
    public TokenExpiredException(String message) {
        super(message);
    }

    /**
     * Constructs a new TokenExpiredException with the specified message and cause.
     *
     * @param message The exception message
     * @param cause   The exception cause
     */
    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new TokenExpiredException with a default message.
     */
    public TokenExpiredException() {
        super("Token expired");
    }

    /**
     * Returns a string representation of the TokenExpiredException.
     *
     * @return A string representation of the TokenExpiredException
     */
    @Override
    public String toString() {
        return "TokenExpiredException{message='" + getMessage() + "'}";
    }
}
