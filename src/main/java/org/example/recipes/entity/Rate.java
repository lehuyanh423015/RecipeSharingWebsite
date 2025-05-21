// src/main/java/org/example/recipes/rate/Rate.java
package org.example.recipes.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @Column(name="rate_id", length=10)
    private String rateId;

    @Column(name="recipe_id", length=10, nullable=false)
    private String recipeId;

    @Column(name="user_id", length=10)
    private String userId;

    @Column(name="rating", nullable=false)
    private int rating;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    public Rate() {}

    public String getRateId() {
        return rateId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
