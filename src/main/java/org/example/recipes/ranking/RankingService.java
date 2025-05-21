package org.example.recipes.ranking;

import org.example.recipes.entity.Recipes;
import java.util.List;
import java.util.Optional;

public interface RankingService {
    /**
     * Lấy top N recipes theo metric và (tùy chọn) category.
     *
     * @param category tên category (empty => lấy toàn hệ thống)
     * @param metric   tên trường metric để sắp xếp: "likeCount", "commentCount", "saveCount", "averageRating", v.v.
     * @param limit    số phần tử cần trả về
     */
    List<Recipes> getTopRecipes(Optional<String> category, String metric, int limit);
}
