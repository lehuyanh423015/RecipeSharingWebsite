package org.example.recipes.media;

import org.example.recipes.entity.Media;
import org.example.recipes.login.IdGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaServiceImpl implements MediaService {
    private final MediaRepository repo;
    private final IdGeneratorService idGenerator;

    public MediaServiceImpl(MediaRepository repo, IdGeneratorService idGenerator) {
        this.repo = repo;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<String> getMediaUrls(String recipeId) {
        return repo.findByRecipeId(recipeId)
                .stream()
                .map(Media::getFileUrl)
                .collect(Collectors.toList());
    }

    @Override
    public List<Media> getByRecipeId(String recipeId) {
        return repo.findByRecipeId(recipeId);
    }

    @Override
    @Transactional
    public void addMedia(String recipeId, String fileUrl, String mediaType) {
        Media m = new Media();
        m.setMediaId(idGenerator.generateId());
        m.setRecipeId(recipeId);
        m.setFileUrl(fileUrl);
        m.setMediaType(mediaType);
        m.setUploadTime(LocalDateTime.now());
        repo.save(m);
    }

    @Override
    @Transactional
    public void deleteMedia(String mediaId) {
        repo.deleteById(mediaId);
    }
}
