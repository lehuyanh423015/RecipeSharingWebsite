package org.example.recipes.serviceTest;

import org.example.recipes.entity.Comment;
import org.example.recipes.comment.CommentRepository;
import org.example.recipes.comment.CommentServiceImpl;
import org.example.recipes.login.IdGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository repo;

    @Mock
    private IdGeneratorService idGenerator;

    @InjectMocks
    private CommentServiceImpl service;

    @Captor
    private ArgumentCaptor<Comment> commentCaptor;


    @Test
    void countComments_delegatesToRepository() {
        when(repo.countByRecipeId("r1")).thenReturn(5);
        int count = service.countComments("r1");
        assertThat(count).isEqualTo(5);
        verify(repo).countByRecipeId("r1");
    }

    @Test
    void listComments_delegatesToRepository() {
        Comment c1 = new Comment();
        c1.setCommentId("ID00000001");
        when(repo.findByRecipeIdOrderByCreatedAtDesc("rX"))
                .thenReturn(List.of(c1));

        List<Comment> list = service.listComments("rX");
        assertThat(list).containsExactly(c1);
        verify(repo).findByRecipeIdOrderByCreatedAtDesc("rX");
    }

    @Test
    void addComment_usesIdGenerator_and_savesCorrectly() {
        when(idGenerator.generateId()).thenReturn("ID00000001");
        service.addComment("userA", "recipeB", "Hello!");

        verify(idGenerator).generateId();
        verify(repo).save(commentCaptor.capture());
        Comment saved = commentCaptor.getValue();

        assertThat(saved.getCommentId()).isEqualTo("ID00000001");
        assertThat(saved.getUserId()).isEqualTo("userA");
        assertThat(saved.getRecipeId()).isEqualTo("recipeB");
        assertThat(saved.getContent()).isEqualTo("Hello!");
        assertThat(saved.getCreatedAt())
                .isCloseTo(LocalDateTime.now(), within(2, ChronoUnit.SECONDS));
    }

    @Test
    void deleteComment_delegatesToRepository() {
        service.deleteComment("ID00000001");
        verify(repo).deleteById("ID00000001");
    }
}
