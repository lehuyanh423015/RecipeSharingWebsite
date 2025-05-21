package org.example.recipes.like;

import org.example.recipes.entity.Like;
import org.example.recipes.login.IdGeneratorService;
import org.example.recipes.recipe.RecipeService;
import org.example.recipes.entity.Recipes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository repo;
    private final RecipeService recipeService;
    private final IdGeneratorService idGen;

    public LikeServiceImpl(
            LikeRepository repo,
            RecipeService recipeService,
            IdGeneratorService idGen
    ) {
        this.repo = repo;
        this.recipeService = recipeService;
        this.idGen = idGen;
    }

    @Override
    public int countLikes(String recipeId) {
        return repo.countByRecipeId(recipeId);
    }

    @Override
    public boolean hasLiked(String userId, String recipeId) {
        return repo.existsByUserIdAndRecipeId(userId, recipeId);
    }

    @Override
    @Transactional
    public boolean toggleLike(String userId, String recipeId) {
        if (!hasLiked(userId, recipeId)) {
            Like e = new Like();
            e.setLikeId(idGen.generateId());
            e.setUserId(userId);
            e.setRecipeId(recipeId);
            e.setCreatedAt(java.time.LocalDateTime.now());
            repo.save(e);
            return true;
        }
        else {
            unlike(userId, recipeId);
            return false;
        }
    }

    @Override
    @Transactional
    public void unlike(String userId, String recipeId) {
        if (repo.findByUserIdAndRecipeId(userId, recipeId) != null) {
            repo.deleteByUserIdAndRecipeId(userId, recipeId);
        }
    }

    @Override
    public List<Recipes> getLikedRecipes(String userId) {
        return repo.findByUserId(userId).stream()
                .map(like -> recipeService.findById(like.getRecipeId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Recipe không tồn tại: " + like.getRecipeId())))
                .collect(Collectors.toList());
    }
}