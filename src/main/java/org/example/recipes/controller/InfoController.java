package org.example.recipes.controller;

import jakarta.servlet.http.HttpSession;
import org.example.recipes.recipe.RecipeService;
import org.example.recipes.recipe.RecipeSimpleDTO;
import org.example.recipes.user.UserPublicDTO;
import org.example.recipes.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class InfoController {
    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // trả về username
        }
        return null;
    }

    /// hiển thị trang cá nhân của người dùng
    @GetMapping("/profile")
    public String profilePage(Model model) {
        String currentUsername = getCurrentUsername();
        UserPublicDTO user = userService.getPublicProfile(currentUsername);
        List<RecipeSimpleDTO> posts = userService.getPostedRecipes(currentUsername);
        List<RecipeSimpleDTO> saves = userService.getSavedRecipes(currentUsername);
        List<RecipeSimpleDTO> likes = userService.getLikedRecipes(currentUsername);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("saves", saves);
        model.addAttribute("likes", likes);
        return "users/profile"; // templates/profile.html
    }

    /// trang chỉnh sửa trang cá nhân
    @GetMapping("/edit-profile")
    public String editProfilePage(Model model) {
        String currentUsername = getCurrentUsername();
        UserPublicDTO user = userService.getPublicProfile(currentUsername);
        model.addAttribute("user", user);
        return "users/edit";
    }

    /// xem trang cua nguoi khac
    @GetMapping("/user/{username}")
    public String viewOtherProfile(@PathVariable String username, Model model) {
        String currentUsername = getCurrentUsername();
        // Lấy thông tin người dùng được xem
        UserPublicDTO user = userService.getPublicProfile(username);

        // Lấy danh sách bài viết đã đăng
        List<RecipeSimpleDTO> posts = userService.getPostedRecipes(username);

        // Kiểm tra người xem có phải chính chủ không
        boolean isOwner = username.equals(currentUsername);
        if (isOwner) {
            return "redirect:/profile";
        }
        // Truyền sang view
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "users/other-profile"; // Tương ứng templates/user-profile.html

    }


}