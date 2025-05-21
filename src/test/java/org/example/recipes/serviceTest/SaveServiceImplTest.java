package org.example.recipes.serviceTest;

import org.example.recipes.login.IdGeneratorService;
import org.example.recipes.entity.Save;
import org.example.recipes.save.SaveRepository;
import org.example.recipes.save.SaveServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveServiceImplTest {

    @Mock
    private SaveRepository repo;

    @Mock
    private IdGeneratorService idGenerator;

    @InjectMocks
    private SaveServiceImpl service;

    @Captor
    private ArgumentCaptor<Save> saveCaptor;

    private final String userId = "userA";
    private final String recipeId = "recipeX";

    @Test
    void save_savesEntityWhenValidInput() {
        when(idGenerator.generateId()).thenReturn("ID00000005");
        service.toggleSave(userId, recipeId);
        verify(repo).save(saveCaptor.capture());
        Save saved = saveCaptor.getValue();
        assertThat(saved.getSaveId()).isEqualTo("ID00000005");
        assertThat(saved.getUserId()).isEqualTo(userId);
        assertThat(saved.getRecipeId()).isEqualTo(recipeId);
        assertThat(saved.getCreatedAt())
                .isCloseTo(LocalDateTime.now(), within(2, ChronoUnit.SECONDS));
    }

    @Test
    void unsave_deletesEntityWhenExists() {
        Save existing = new Save();
        existing.setSaveId("ID00000006");
        existing.setUserId(userId);
        existing.setRecipeId(recipeId);
        when(repo.findByUserIdAndRecipeId(userId, recipeId)).thenReturn(existing);

        service.unsave(userId, recipeId);
        verify(repo).delete(existing);
    }

    @Test
    void unsave_doesNothingWhenNotExists() {
        when(repo.findByUserIdAndRecipeId(userId, recipeId)).thenReturn(null);
        service.unsave(userId, recipeId);
        verify(repo, never()).delete(any());
    }

    @Test
    void unsave_throwsOnInvalidInput() {
        assertThatThrownBy(() -> service.unsave("", recipeId))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.unsave(userId, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}