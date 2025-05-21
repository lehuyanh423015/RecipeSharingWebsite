// src/main/java/org/example/recipes/media/MediaService.java
package org.example.recipes.media;

import org.example.recipes.entity.Media;

import java.util.List;

public interface MediaService {
    List<String> getMediaUrls(String recipeId);
    List<Media> getByRecipeId(String recipeId);
    void addMedia(String recipeId, String fileUrl, String mediaType);
    void deleteMedia(String mediaId);
}
