package org.example.recipes.category;

import org.example.recipes.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    /**
     * Suggest categories by prefix sorted by how often they are used.
     */
    List<Category> findByNameStartingWithIgnoreCaseOrderByUsageCountDesc(String prefix);

    /**
     * Get all categories ordered alphabetically.
     */
    List<Category> findAllByOrderByNameAsc();

    /**
     * Get top categories by usage count (paged).
     */
    List<Category> findAllByOrderByUsageCountDesc(Pageable pageable);
    Optional<Category> findByCategoryId(String categoryId);
}