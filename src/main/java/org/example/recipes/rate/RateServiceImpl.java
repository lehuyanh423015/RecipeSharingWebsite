// src/main/java/org/example/recipes/rate/RateServiceImpl.java
package org.example.recipes.rate;

import org.example.recipes.entity.Rate;
import org.example.recipes.login.IdGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RateServiceImpl implements RateService {
    private final RateRepository repo;
    private final IdGeneratorService idGenerator;

    public RateServiceImpl(RateRepository repo, IdGeneratorService idGenerator) {
        this.repo = repo;
        this.idGenerator = idGenerator;
    }

    @Override
    public float getAverageRating(String recipeId) {
        Float avg = repo.findAverageRating(recipeId);
        return avg == null ? 0f : avg;
    }

    @Override
    public Integer getUserRating(String userId, String recipeId) {
        Rate e = repo.findByUserIdAndRecipeId(userId, recipeId);
        return e == null ? null : e.getRating();
    }

    @Override
    @Transactional
    public void rate(String userId, String recipeId, int rating) {
        Rate e = repo.findByUserIdAndRecipeId(userId, recipeId);
        if (e == null) {
            e = new Rate();
            e.setRateId(idGenerator.generateId());
            e.setUserId(userId);
            e.setRecipeId(recipeId);
            e.setCreatedAt(java.time.LocalDateTime.now());
        }
        e.setRating(rating);
        repo.save(e);
    }

    @Override
    @Transactional
    public void removeRating(String userId, String recipeId) {
        Rate e = repo.findByUserIdAndRecipeId(userId, recipeId);
        if (e != null) repo.delete(e);
    }

    @Override
    public int countRating(String recipeId) {
        // Trả về số lượt đánh giá
        return repo.countByRecipeId(recipeId);
    }
}
