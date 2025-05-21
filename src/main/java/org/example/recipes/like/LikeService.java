package org.example.recipes.like;

import org.example.recipes.entity.Recipes;

import java.util.List;

public interface LikeService {
    int countLikes(String recipeId);
    boolean hasLiked(String userId, String recipeId);
    boolean toggleLike(String userId, String recipeId);
    void unlike(String userId, String recipeId);
    List<Recipes> getLikedRecipes(String userId);
}