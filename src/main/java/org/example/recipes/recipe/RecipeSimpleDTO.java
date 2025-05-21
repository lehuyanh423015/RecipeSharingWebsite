package org.example.recipes.recipe;

import org.example.recipes.comment.CommentDTO;

import java.util.List;

public class RecipeSimpleDTO {
    private String id; // Changed from Long to String
    private String title;
    private String imageUrl;
    private double averageRating;
    private int likeCount;
    private List<CommentDTO> comments;

    public RecipeSimpleDTO() {}

    public RecipeSimpleDTO(String id, String title, String imageUrl, double averageRating, int likeCount, List<CommentDTO> comments) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.averageRating = averageRating;
        this.likeCount = likeCount;
        this.comments = comments;
    }

    public String getId() { // Updated to return String
        return id;
    }

    public void setId(String id) { // Updated to accept String
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getLikeCount() { return likeCount; }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}