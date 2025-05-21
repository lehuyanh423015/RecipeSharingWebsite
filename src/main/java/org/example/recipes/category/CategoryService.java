package org.example.recipes.category;

import org.example.recipes.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * Ghi nhận (tăng usageCount) hoặc tạo mới category với tên cho trước.
     */
    void recordCategory(String name);

    /**
     * Gợi ý tên category bắt đầu bằng prefix, sắp xếp theo usageCount giảm dần.
     */
    List<String> suggest(String prefix);

    /**
     * Lấy danh sách tất cả tên category, sắp xếp theo tên tăng dần.
     */
    List<String> getAllCategories();

    /**
     * Lấy danh sách top N tên category được dùng nhiều nhất.
     */
    List<String> getTopCategories(int limit);

    // Các phương thức dành cho phần Admin:

    /**
     * Lấy danh sách đầy đủ các Category (entity).
     */
    List<Category> getAll();

    /**
     * Thêm mới một Category (trong request body có name, usageCount).
     * Hệ thống sẽ tự sinh categoryId.
     */
    void addCategory(Category category);

    /**
     * Cập nhật Category theo categoryId (String).
     */
    void updateCategory(String categoryId, Category category);

    /**
     * Xóa Category theo categoryId (String).
     */
    void deleteCategory(String categoryId);
}
