package org.example.recipes.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "recipes")
@SecondaryTable(
        name = "recipe_stats",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "recipe_id")
)
public class Recipes {
    @Id
    @Column(name = "recipe_id", length = 10)
    private String recipeId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "instruction", length = 1000, nullable = false)
    private String instruction;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category_name", length = 100)
    private String category;

    @Column(name = "category_id", length = 10)
    private String categoryId;

    @Column(name = "ingredients", columnDefinition = "TEXT", nullable = false)
    private String ingredients;

    @Column(name = "author_name", length = 100)
    private String authorName;

    @Column(name = "author_id", length = 10)
    private String authorId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column(name = "author_url", length = 255)
    private String authorUrl;

    // --- thống kê từ recipe_stats ---
    @Column(table = "recipe_stats", name = "like_count")
    private int likeCount;

    @Column(table = "recipe_stats", name = "save_count")
    private int saveCount;

    @Column(table = "recipe_stats", name = "comment_count")
    private int commentCount;

    @Column(table = "recipe_stats", name = "rate_count")
    private int rateCount;

    @Column(table = "recipe_stats", name = "average_rating")
    private float averageRating;

    public Recipes() {}


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getRateCount() {
        return rateCount;
    }

    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getSaveCount() {
        return saveCount;
    }

    public void setSaveCount(Integer saveCount) {
        this.saveCount = saveCount;
    }
}