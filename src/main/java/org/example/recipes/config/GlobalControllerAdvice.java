package org.example.recipes.config;

import jakarta.servlet.http.HttpSession;
import org.example.recipes.entity.Users;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("user")
    public Users currentUser(HttpSession session) {
        return (Users) session.getAttribute("user");
    }
}