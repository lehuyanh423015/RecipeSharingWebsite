package org.example.recipes.controller;

import jakarta.servlet.http.HttpSession;
import org.example.recipes.recipe.RecipeSimpleDTO;
import org.example.recipes.user.UserProfileUpdatedDTO;
import org.example.recipes.user.UserPublicDTO;
import org.example.recipes.user.UserService;
import org.example.recipes.user.UserSimpleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserProfileController {

    private final UserService userService;



    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    /// api show profile
    @GetMapping("/{username}/public")
    public ResponseEntity<UserPublicDTO> getPublicProfile(@PathVariable String username) {
        UserPublicDTO dto = userService.getPublicProfile(username);
        return ResponseEntity.ok(dto);
    }

    /// api show following
    @GetMapping("/{username}/following")
    public ResponseEntity<List<UserSimpleDTO>> getFollowing(@PathVariable String username) {
        List<UserSimpleDTO> following = userService.getFollowing(username);
        return ResponseEntity.ok(following);
    }

    /// api show follower
    @GetMapping("/{username}/follower")
    public ResponseEntity<List<UserSimpleDTO>> getFollowers(@PathVariable String username) {
        List<UserSimpleDTO> followers = userService.getFollowers(username);
        return ResponseEntity.ok(followers);
    }

    /// api show bài đăng
    @GetMapping("/{username}/posts")
    public ResponseEntity<List<RecipeSimpleDTO>> getPosts(@PathVariable String username) {
        List<RecipeSimpleDTO> recipes = userService.getPostedRecipes(username);
        return ResponseEntity.ok(recipes);
    }

    /// api show bài đã lưu
    @GetMapping("/{username}/saves")
    public ResponseEntity<List<RecipeSimpleDTO>> getSavedRecipes(@PathVariable String username) {
        List<RecipeSimpleDTO> savedRecipes = userService.getSavedRecipes(username);
        return ResponseEntity.ok(savedRecipes);
    }

    /// api show bài đã thích
    @GetMapping("/{username}/likes")
    public ResponseEntity<List<RecipeSimpleDTO>> getLikedRecipes(@PathVariable String username) {
        List<RecipeSimpleDTO> likedRecipes = userService.getLikedRecipes(username);
        return ResponseEntity.ok(likedRecipes);
    }

    /// api chỉnh sửa
    @PutMapping("/{username}/edit")
    public ResponseEntity<String> updateUserProfile(
            @PathVariable String username,
            @RequestBody UserProfileUpdatedDTO dto,
            HttpSession session) {

        userService.updateUserInfo(username, dto);

        // 🔁 Cập nhật session nếu username đã thay đổi
        session.setAttribute("username", dto.getUsername());

        return ResponseEntity.ok("Cập nhật thành công");
    }
}