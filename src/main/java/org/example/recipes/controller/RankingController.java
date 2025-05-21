package org.example.recipes.controller;

import org.example.recipes.ranking.RankingService;
import org.example.recipes.entity.Recipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    /**
     * GET /ranking
     * @param category (optional) lọc theo category
     * @param metric   (required) tên metric: likeCount, commentCount, saveCount, averageRating,...
     * @param limit    (optional) số lượng trả về, default = 10
     */
    @GetMapping
    public List<Recipes> getRanking(
            @RequestParam Optional<String> category,
            @RequestParam String metric,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return rankingService.getTopRecipes(category, metric, limit);
    }
}
