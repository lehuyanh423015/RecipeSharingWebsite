package org.example.recipes.exception;

/**
 * Thrown when attempting to register with a username that already exists.
 */
public class UsernameExistsException extends BusinessException {
    public UsernameExistsException() {
        super("Username đã tồn tại.");
    }

    public UsernameExistsException(String message) {
        super(message);
    }

    public UsernameExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameExistsException(Throwable cause) {
        super(cause);
    }
}