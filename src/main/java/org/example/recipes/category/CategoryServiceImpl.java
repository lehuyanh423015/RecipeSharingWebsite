package org.example.recipes.category;

import org.example.recipes.entity.Category;
import org.example.recipes.exception.BusinessException;
import org.example.recipes.login.IdGeneratorService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;
    private final IdGeneratorService idGen;

    public CategoryServiceImpl(CategoryRepository repo,
                               IdGeneratorService idGen) {
        this.repo  = repo;
        this.idGen = idGen;
    }

    @Override
    @Transactional
    public void recordCategory(String name) {
        repo.findById(name)
                .ifPresentOrElse(
                        Category::increment,
                        () -> repo.save(new Category(name))
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> suggest(String prefix) {
        return repo.findByNameStartingWithIgnoreCaseOrderByUsageCountDesc(prefix)
                .stream()
                .map(Category::getName)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return repo.findAllByOrderByNameAsc()
                .stream()
                .map(Category::getName)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getTopCategories(int limit) {
        return repo.findAllByOrderByUsageCountDesc(PageRequest.of(0, limit))
                .stream()
                .map(Category::getName)
                .toList();
    }

    // ==== admin ====

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAll() {
        return repo.findAllByOrderByNameAsc();
    }

    @Override
    @Transactional
    public void addCategory(Category category) {
        if (repo.existsById(category.getName())) {
            throw new BusinessException("Category đã tồn tại: " + category.getName());
        }
        category.setCategoryId(idGen.generateId());  // ID kiểu String
        repo.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(String categoryId, Category incoming) {
        Category exist = repo.findByCategoryId(categoryId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy Category id=" + categoryId));
        exist.setName(incoming.getName());
        exist.setUsageCount(incoming.getUsageCount());
        repo.save(exist);
    }

    @Override
    @Transactional
    public void deleteCategory(String categoryId) {
        Category exist = repo.findByCategoryId(categoryId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy Category id=" + categoryId));
        repo.delete(exist);
    }
}
