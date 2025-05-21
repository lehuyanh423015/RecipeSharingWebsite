package org.example.recipes.comment;

import org.example.recipes.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    int countByRecipeId(String recipeId);
    List<Comment> findByRecipeId(String recipeId);
    List<Comment> findByRecipeIdOrderByCreatedAtDesc(String recipeId);
}