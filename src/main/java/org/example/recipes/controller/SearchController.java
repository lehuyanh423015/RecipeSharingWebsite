package org.example.recipes.controller;

import org.example.recipes.entity.Recipes;
import org.example.recipes.recipe.RecipeDetailDTO;
import org.example.recipes.recipe.RecipeService;
import org.example.recipes.user.UserPublicDTO;
import org.example.recipes.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final UserService userService;
    private final RecipeService recipeService;

    public SearchController(UserService userService, RecipeService recipeService) {
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "q", required = false) String q,
            Model model
    ) {
        model.addAttribute("query", q);

        // giả lập: bạn có thể thay bằng gọi service thật
        List<UserPublicDTO> users = userService.searchUsers(q);
        List<RecipeDetailDTO>recipes = recipeService.searchByName(q);

        model.addAttribute("users", users);
        model.addAttribute("recipes", recipes);

        return "search";
    }

}
