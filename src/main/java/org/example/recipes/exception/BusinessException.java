package org.example.recipes.exception;

/**
 * Base runtime exception for business logic errors.
 * Unchecked to avoid boilerplate handling and let global handler manage responses.
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}