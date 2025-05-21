package org.example.recipes.controller;

import org.example.recipes.admin.AdminService;
import org.example.recipes.entity.Category;
import org.example.recipes.category.CategoryService;
import org.example.recipes.recipe.RecipeSimpleDTO;
import org.example.recipes.user.UserPublicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private CategoryService categoryService;

    // users list
    @GetMapping("/users")
    public List<UserPublicDTO> getAllUsers() {
        return adminService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok("Da xoa nguoi dung thanh cong");
    }

    // danh sach tat ca bai viet (hoac bai vi pham)
    @GetMapping("/recipes")
    public List<RecipeSimpleDTO> getAllRecipes() {
        return adminService.getAllRecipes();
    }

    // xoa bai viet
    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String recipeId) {
        adminService.deleteRecipe(recipeId);
        return ResponseEntity.ok("Da xoa cong thuc thanh cong");
    }

    // danh sach category
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }

    // them the loai moi
    @PostMapping("/categories")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        try {
            categoryService.addCategory(category);
            return ResponseEntity.ok("Đã thêm thể loại mới");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // cap nhat the loai
    @PutMapping("/categories/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable String id, @RequestBody Category category) {
        categoryService.updateCategory(id, category);
        return ResponseEntity.ok("Da cap nhat the loai thanh cong");
    }

    // xoa the loai
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Da xoa the loai.");
    }
}