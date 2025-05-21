package org.example.recipes.serviceTest;

import org.example.recipes.login.IdGeneratorService;
import org.example.recipes.entity.Media;
import org.example.recipes.media.MediaRepository;
import org.example.recipes.media.MediaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaServiceImplTest {

    @Mock
    private MediaRepository repo;

    @Mock
    private IdGeneratorService idGenerator;

    @InjectMocks
    private MediaServiceImpl service;

    @Captor
    private ArgumentCaptor<Media> mediaCaptor;

    private final String recipeId = "R123";
    private final String fileUrl  = "http://example.com/img.jpg";
    private final String mediaType = "image";

    @Test
    void getMediaUrls_returnsFileUrls() {
        Media m1 = new Media();
        m1.setFileUrl("u1");
        Media m2 = new Media();
        m2.setFileUrl("u2");
        when(repo.findByRecipeId(recipeId)).thenReturn(List.of(m1, m2));

        List<String> urls = service.getMediaUrls(recipeId);

        assertThat(urls).containsExactly("u1", "u2");
        verify(repo).findByRecipeId(recipeId);
    }

    @Test
    void getByRecipeId_returnsMediaList() {
        Media m = new Media();
        when(repo.findByRecipeId(recipeId)).thenReturn(List.of(m));

        List<Media> list = service.getByRecipeId(recipeId);

        assertThat(list).containsExactly(m);
        verify(repo).findByRecipeId(recipeId);
    }

    @Test
    void addMedia_generatesIdAndSavesEntity() {
        when(idGenerator.generateId()).thenReturn("ID00000003");
        service.addMedia(recipeId, fileUrl, mediaType);

        verify(idGenerator).generateId();
        verify(repo).save(mediaCaptor.capture());
        Media saved = mediaCaptor.getValue();

        assertThat(saved.getMediaId()).isEqualTo("ID00000003");
        assertThat(saved.getRecipeId()).isEqualTo(recipeId);
        assertThat(saved.getFileUrl()).isEqualTo(fileUrl);
        assertThat(saved.getMediaType()).isEqualTo(mediaType);
        assertThat(saved.getUploadTime())
                .isCloseTo(LocalDateTime.now(), within(2, ChronoUnit.SECONDS));
    }

    @Test
    void deleteMedia_delegatesToRepository() {
        String mediaId = "ID00000003";
        service.deleteMedia(mediaId);
        verify(repo).deleteById(mediaId);
    }
}
