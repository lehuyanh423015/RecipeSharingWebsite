package org.example.recipes.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name="comment_id", length=10)
    private String commentId;

    @Column(name="recipe_id", length=10, nullable=false)
    private String recipeId;

    @Column(name="user_id", length=10)
    private String userId;

    @Column(name="comment_content", columnDefinition="TEXT", nullable=false)
    private String content;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    public Comment() {}

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}