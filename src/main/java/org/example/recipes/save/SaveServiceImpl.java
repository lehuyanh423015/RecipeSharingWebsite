package org.example.recipes.save;

import org.example.recipes.entity.Save;
import org.example.recipes.login.IdGeneratorService;
import org.example.recipes.recipe.RecipeService;
import org.example.recipes.entity.Recipes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaveServiceImpl implements SaveService {
    private final SaveRepository saveRepo;
    private final RecipeService recipeService;    // cần có service này
    private final IdGeneratorService idGen;

    public SaveServiceImpl(
            SaveRepository saveRepo,
            RecipeService recipeService,
            IdGeneratorService idGen
    ) {
        this.saveRepo = saveRepo;
        this.recipeService = recipeService;
        this.idGen = idGen;
    }

    @Override
    public int countSaves(String recipeId) {
        return saveRepo.countByRecipeId(recipeId);
    }

    @Override
    public boolean hasSaved(String userId, String recipeId) {
        return saveRepo.existsByUserIdAndRecipeId(userId, recipeId);
    }

    @Override
    @Transactional
    public boolean toggleSave(String userId, String recipeId) {
        if (userId == null || userId.isBlank() || recipeId == null || recipeId.isBlank()) {
            throw new IllegalArgumentException("userId and recipeId must not be null or blank");
        }
        if (!hasSaved(userId, recipeId)) {
            Save e = new Save();
            e.setSaveId(idGen.generateId());
            e.setUserId(userId);
            e.setRecipeId(recipeId);
            e.setCreatedAt(LocalDateTime.now());
            saveRepo.save(e);
            return true;
        }
        else {
            unsave(userId, recipeId);
            return false;
        }
    }

    @Override
    @Transactional
    public void unsave(String userId, String recipeId) {
        if (userId == null || userId.isBlank() || recipeId == null || recipeId.isBlank()) {
            throw new IllegalArgumentException("userId and recipeId must not be null or blank");
        }
        Save e = saveRepo.findByUserIdAndRecipeId(userId, recipeId);
        if (e != null) {
            saveRepo.delete(e);
        }
    }

    @Override
    public List<Recipes> getSavedRecipes(String userId) {
        return saveRepo.findByUserId(userId).stream()
                .map(save -> {
                    // giả sử RecipeService có method findById
                    return recipeService.findById(save.getRecipeId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Recipe không tồn tại: " + save.getRecipeId()));
                })
                .collect(Collectors.toList());
    }
}