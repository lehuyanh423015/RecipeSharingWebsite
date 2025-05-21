// src/main/java/org/example/recipes/rate/RateService.java
package org.example.recipes.rate;

public interface RateService {
    float getAverageRating(String recipeId);
    Integer getUserRating(String userId, String recipeId);
    void rate(String userId, String recipeId, int rating);
    void removeRating(String userId, String recipeId);
    int countRating(String recipeId);
}
