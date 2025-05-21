package org.example.recipes.admin;

import org.example.recipes.recipe.RecipeSimpleDTO;
import org.example.recipes.user.UserPublicDTO;

import java.util.List;

public interface AdminService {
    List<UserPublicDTO> getAllUsers();
    void deleteUser(String userId);
    List<RecipeSimpleDTO> getAllRecipes();
    void deleteRecipe(String recipeId);

}