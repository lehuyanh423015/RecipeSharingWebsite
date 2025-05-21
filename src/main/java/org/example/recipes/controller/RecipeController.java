package org.example.recipes.controller;

import org.example.recipes.category.CategoryService;
import org.example.recipes.entity.Users;
import org.example.recipes.recipe.RecipeService;
import org.example.recipes.entity.Recipes;
import org.example.recipes.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;
    private final UserService userService;

    public RecipeController(RecipeService recipeService,
                            CategoryService categoryService,
                            UserService userService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    private Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            User springUser = (User) authentication.getPrincipal(); // Lấy thông tin người dùng từ Spring Security
            // Giả định bạn có dịch vụ để tìm `Users` từ database dựa trên tên đăng nhập.
            return userService.findByUsername(springUser.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        }
        return null;
    }

    // Hiển thị form tạo mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Users user = getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("recipeForm", new Recipes());
        return "recipes/new";
    }

    // Xử lý submit form tạo
    @PostMapping("/new")
    public String createRecipe(@ModelAttribute("recipeForm") Recipes recipeForm) {
        recipeService.create(recipeForm);
        return "redirect:/";
    }

    // Hiển thị form sửa
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Recipes recipe = recipeService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recipe not found: " + id));
        model.addAttribute("recipeForm", recipe);
        model.addAttribute("id", id);
        return "recipes/edit";
    }

    // Xử lý submit form sửa
    @PostMapping("/{id}/edit")
    public String updateRecipe(@PathVariable("id") String id,
                               @ModelAttribute("recipeForm") Recipes recipeForm) {
        recipeService.update(id, recipeForm);
        return "redirect:/recipes/" + id;
    }

    // Xoá recipe
    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable("id") String id) {
        recipeService.delete(id);
        return "redirect:/";
    }

    // Gợi ý category trả về JSON
    @GetMapping("/categories/suggest")
    @ResponseBody
    public List<String> suggestCategories(@RequestParam("q") String q) {
        return categoryService.suggest(q);
    }
}
