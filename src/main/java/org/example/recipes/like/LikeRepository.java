package org.example.recipes.like;

import org.example.recipes.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, String> {
    int countByRecipeId(String recipeId);
    boolean existsByUserIdAndRecipeId(String userId, String recipeId);
    Like findByUserIdAndRecipeId(String userId, String recipeId);
    void deleteByUserIdAndRecipeId(String userId, String recipeId);
    List<Like> findByUserId(String userId);
}