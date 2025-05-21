package org.example.recipes.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "media")
public class Media {
    @Id
    @Column(name="media_id", length=10)
    private String mediaId;

    @Column(name="recipe_id", length=10, nullable=false)
    private String recipeId;

    @Column(name="file_url", length=255, nullable=false)
    private String fileUrl;

    @Column(name="media_type", length=20)
    private String mediaType; // "image" or "video"

    @Column(name="upload_time")
    private LocalDateTime uploadTime;

    public Media() {
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
}