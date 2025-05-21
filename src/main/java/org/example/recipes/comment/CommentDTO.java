package org.example.recipes.comment;

import java.time.LocalDateTime;

public class CommentDTO {
    public String commentId;
    public String username;
    public String content;
    public LocalDateTime createdAt;

    public CommentDTO() {
    }

    public CommentDTO(String username, String content, LocalDateTime createdAt) {
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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