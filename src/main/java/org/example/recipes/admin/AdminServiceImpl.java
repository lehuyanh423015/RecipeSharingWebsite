package org.example.recipes.admin;

import org.example.recipes.recipe.RecipeSimpleDTO;
import org.example.recipes.recipe.RecipeService;
import org.example.recipes.user.UserPublicDTO;
import org.example.recipes.user.UserService;
import org.example.recipes.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository   userRepo;
    private final UserService      userService;
    private final RecipeService    recipeService;

    public AdminServiceImpl(UserRepository userRepo,
                            UserService userService,
                            RecipeService recipeService) {
        this.userRepo      = userRepo;
        this.userService   = userService;
        this.recipeService = recipeService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPublicDTO> getAllUsers() {
        // Lấy tất cả Users rồi map qua UserPublicDTO qua UserService
        return userRepo.findAll().stream()
                .map(u -> userService.getPublicProfile(u.getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        // Giả sử khóa chính của Users là kiểu String (username) dưới dạng số,
        // ta gọi deleteById(id.toString()). Nếu thực sự PK là Long thì sửa UserRepository generic type.
        userRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeSimpleDTO> getAllRecipes() {
        // Lấy tất cả recipes, map qua DTO
        return recipeService.findAll().stream()
                .map(r -> {
                    RecipeSimpleDTO d = new RecipeSimpleDTO();
                    // Nếu recipeId là String, có thể parseLong; sửa theo thực tế
                    d.setId(r.getRecipeId());
                    d.setTitle(r.getName());
                    d.setImageUrl(r.getAvatarUrl());
                    d.setAverageRating(r.getAverageRating());
                    d.setLikeCount(r.getLikeCount());
                    // Lấy commentDTO từ commentService nếu muốn, hoặc để rỗng
                    d.setComments(List.of());
                    return d;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteRecipe(String recipeId) {
        // Tương tự, nếu recipeId là String, đổi tham số thành String
        recipeService.delete(recipeId);
    }
}
