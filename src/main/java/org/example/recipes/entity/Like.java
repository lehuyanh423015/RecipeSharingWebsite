package org.example.recipes.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
public class Like {
    @Id
    @Column(name = "like_id", length = 10)
    private String likeId;

    @Column(name = "recipe_id", length = 10, nullable = false)
    private String recipeId;

    @Column(name = "user_id", length = 10)
    private String userId;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    public Like() {}

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}