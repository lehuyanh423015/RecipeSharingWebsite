package org.example.recipes.news_feed;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO đại diện cho 1 mục trong news-feed.
 */
public class RecipeFeedItem {

    private String recipeId;
    private String name;
    private String description;
    private String category;

    private String authorId;
    private String authorName;
    private String authorAvatarUrl; // nếu có

    private LocalDateTime createdAt;
    private List<String> mediaUrls;

    private int likeCount;
    private int commentCount;
    private int saveCount;
    private int rateCount;
    private float averageRating;

    private boolean likedByMe;
    private boolean savedByMe;
    private boolean followingAuthor;
    private Integer myRating;  // null nếu chưa đánh giá

    public RecipeFeedItem() { }

    // === Getters & Setters ===

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

    public String getAuthorId() {
        return authorId;
    }
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatarUrl() {
        return authorAvatarUrl;
    }
    public void setAuthorAvatarUrl(String authorAvatarUrl) {
        this.authorAvatarUrl = authorAvatarUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }
    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public int getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }
    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getSaveCount() {
        return saveCount;
    }
    public void setSaveCount(int saveCount) {
        this.saveCount = saveCount;
    }

    public int getRateCount() {
        return rateCount;
    }
    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public float getAverageRating() {
        return averageRating;
    }
    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isLikedByMe() {
        return likedByMe;
    }
    public void setLikedByMe(boolean likedByMe) {
        this.likedByMe = likedByMe;
    }

    public boolean isSavedByMe() {
        return savedByMe;
    }
    public void setSavedByMe(boolean savedByMe) {
        this.savedByMe = savedByMe;
    }

    public boolean isFollowingAuthor() {
        return followingAuthor;
    }
    public void setFollowingAuthor(boolean followingAuthor) {
        this.followingAuthor = followingAuthor;
    }

    public Integer getMyRating() {
        return myRating;
    }
    public void setMyRating(Integer myRating) {
        this.myRating = myRating;
    }

    // === Builder cho tiện sử dụng ===

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final RecipeFeedItem item = new RecipeFeedItem();

        public Builder recipeId(String id)            { item.setRecipeId(id); return this; }
        public Builder name(String n)                 { item.setName(n); return this; }
        public Builder description(String d)          { item.setDescription(d); return this; }
        public Builder category(String c)             { item.setCategory(c); return this; }
        public Builder authorId(String id)            { item.setAuthorId(id); return this; }
        public Builder authorName(String n)           { item.setAuthorName(n); return this; }
        public Builder authorAvatarUrl(String url)    { item.setAuthorAvatarUrl(url); return this; }
        public Builder createdAt(LocalDateTime t)     { item.setCreatedAt(t); return this; }
        public Builder mediaUrls(List<String> m)      { item.setMediaUrls(m); return this; }
        public Builder likeCount(int c)               { item.setLikeCount(c); return this; }
        public Builder commentCount(int c)            { item.setCommentCount(c); return this; }
        public Builder saveCount(int c)               { item.setSaveCount(c); return this; }
        public Builder rateCount(int c)               { item.setRateCount(c); return this; }
        public Builder averageRating(float r)         { item.setAverageRating(r); return this; }
        public Builder likedByMe(boolean b)           { item.setLikedByMe(b); return this; }
        public Builder savedByMe(boolean b)           { item.setSavedByMe(b); return this; }
        public Builder followingAuthor(boolean b)     { item.setFollowingAuthor(b); return this; }
        public Builder myRating(Integer r)            { item.setMyRating(r); return this; }

        public RecipeFeedItem build() {
            return this.item;
        }
    }
}
