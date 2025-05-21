package org.example.recipes.ranking;

import org.example.recipes.recipe.RecipeRepository;
import org.example.recipes.entity.Recipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RankingServiceImpl implements RankingService {

    private final RecipeRepository repo;

    @Autowired
    public RankingServiceImpl(RecipeRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Recipes> getTopRecipes(Optional<String> categoryOpt, String metric, int limit) {
        // Kiểm tra metric hợp lệ
        Sort sort = Sort.by(Sort.Direction.DESC, metric);
        Pageable page = PageRequest.of(0, limit, sort);

        if (categoryOpt.isPresent() && !categoryOpt.get().isBlank()) {
            return repo.findByCategory(categoryOpt.get(), page).getContent();
        } else {
            // Dùng findAll với paging+sort
            return repo.findAll(page).getContent();
        }
    }
}
