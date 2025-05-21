package org.example.recipes.serviceTest;

import org.example.recipes.entity.Like;
import org.example.recipes.like.LikeRepository;
import org.example.recipes.like.LikeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    @Mock
    LikeRepository repo;
    @Mock org.example.recipes.login.IdGeneratorService idGen;
    @InjectMocks
    LikeServiceImpl svc;

    @Test
    void likeRecipe_shouldSaveOnceWhenNotExist() {
        String uid = "U1", rid = "R1";
        when(repo.existsByUserIdAndRecipeId(uid, rid)).thenReturn(false);
        when(idGen.generateId()).thenReturn("ID00000001");

        svc.toggleLike(uid, rid);

        ArgumentCaptor<Like> captor = ArgumentCaptor.forClass(Like.class);
        verify(repo, times(1)).save(captor.capture());
        Like saved = captor.getValue();
        assertEquals("ID00000001", saved.getLikeId());
        assertEquals(rid, saved.getRecipeId());
        assertEquals(uid, saved.getUserId());
    }

    @Test
    void likeRecipe_shouldNotSaveIfAlreadyLiked() {
        when(repo.existsByUserIdAndRecipeId("U1","R1")).thenReturn(true);
        svc.toggleLike("U1","R1");
        verify(repo, never()).save(any());
    }

    @Test
    void hasUserLiked_and_countLikes() {
        when(repo.existsByUserIdAndRecipeId("U","R")).thenReturn(true);
        when(repo.countByRecipeId("R")).thenReturn(5);

        assertTrue(svc.hasLiked("U","R"));
        assertEquals(5, svc.countLikes("R"));
    }

    @Test
    void unlikeRecipe_shouldDeleteWhenInvoked() {
        // Given
        String userId = "U1";
        String recipeId = "R1";

        // Giả lập trả về Like object khi tìm thấy
        when(repo.findByUserIdAndRecipeId(userId, recipeId)).thenReturn(new Like());

        // When
        svc.unlike(userId, recipeId);

        // Then
        verify(repo).findByUserIdAndRecipeId(userId, recipeId);  // Kiểm tra findByUserIdAndRecipeId đã được gọi
        verify(repo).deleteByUserIdAndRecipeId(userId, recipeId);  // Kiểm tra deleteByUserIdAndRecipeId đã được gọi
    }
}
