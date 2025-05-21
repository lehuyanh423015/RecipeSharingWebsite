package org.example.recipes.exception;

/**
 * Thrown when attempting to register with an email that is already in use.
 */
public class EmailExistsException extends BusinessException {
    public EmailExistsException() {
        super("Email đã được sử dụng.");
    }

    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailExistsException(Throwable cause) {
        super(cause);
    }
}
