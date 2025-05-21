package org.example.recipes.controller;

import org.example.recipes.recipe.RecipeDetailDTO;
import org.example.recipes.entity.Recipes;
import org.example.recipes.recipe.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller xử lý trang chi tiết recipe và cung cấp dữ liệu cho Thymeleaf.
 */
@Controller
@RequestMapping("/recipeDetails")
public class IngredientController {

    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Hiển thị trang recipeDetails.html với thông tin RecipeDetailDTO
     * URL: GET /recipeDetails/{id}
     */
    @GetMapping("/{id}")
    public String showRecipeDetails(
            @PathVariable("id") String id,
            Model model) {
        // Lấy entity Recipes từ service
        Recipes recipe = recipeService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Không tìm thấy recipe với id=" + id
                ));

        // Map entity sang DTO
        RecipeDetailDTO dto = new RecipeDetailDTO();
        dto.setRecipeId(recipe.getRecipeId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setInstructions(recipe.getInstruction());
        dto.setIngredients(recipe.getIngredients());
        dto.setCategory(recipe.getCategory());
        dto.setAuthorId(recipe.getAuthorId());
        dto.setAuthorName(recipe.getAuthorName());
        dto.setImageUrl(recipe.getAvatarUrl());
        dto.setCreatedAt(recipe.getCreatedAt());
        dto.setLikeCount(recipe.getLikeCount());
        dto.setAverageRating(recipe.getAverageRating());
        dto.setSaveCount(recipe.getSaveCount());
        // Nếu DTO có thêm trường nào, set thêm ở đây...

        // Đưa DTO vào model để Thymeleaf render
        model.addAttribute("recipe", dto);
        return "recipes/recipeDetails";  // tương ứng templates/recipeDetails.html
    }
}