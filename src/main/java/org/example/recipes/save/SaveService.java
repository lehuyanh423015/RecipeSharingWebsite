// src/main/java/org/example/recipes/save/SaveService.java
package org.example.recipes.save;

import org.example.recipes.entity.Recipes;

import java.util.List;

public interface SaveService {
    int countSaves(String recipeId);
    boolean hasSaved(String userId, String recipeId);
    boolean toggleSave(String userId, String recipeId);
    void unsave(String userId, String recipeId);
    List<Recipes> getSavedRecipes(String userId);
}