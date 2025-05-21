// src/main/java/org/example/recipes/media/MediaRepository.java
package org.example.recipes.media;

import org.example.recipes.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MediaRepository extends JpaRepository<Media, String> {
    List<Media> findByRecipeId(String recipeId);
}
