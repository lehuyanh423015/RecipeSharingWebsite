// src/main/java/org/example/recipes/rate/RateRepository.java
package org.example.recipes.rate;

import org.example.recipes.entity.Rate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate, String> {
    @Query("SELECT AVG(r.rating) FROM Rate r WHERE r.recipeId = ?1")
    Float findAverageRating(String recipeId);

    Rate findByUserIdAndRecipeId(String userId, String recipeId);

    List<Rate> findByOrderByCreatedAtDesc(Pageable pageable);

    int countByRecipeId(String recipeId);
}
