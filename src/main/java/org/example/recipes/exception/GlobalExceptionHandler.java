// src/main/java/org/example/recipes/exception/GlobalExceptionHandler.java
package org.example.recipes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler trả về status code phù hợp và message dưới dạng JSON/plain text,
 * không sử dụng Thymeleaf views để tránh lỗi template.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleBusiness(BusinessException ex) {
        // Trả về message lỗi khi sai business logic (ví dụ username/email tồn tại)
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleAll(Exception ex) {
        // Trả về message chung khi có lỗi hệ thống
        return "Lỗi hệ thống: " + ex.getMessage();
    }
}
