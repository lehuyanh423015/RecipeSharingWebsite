package org.example.recipes.comment;

import org.example.recipes.entity.Comment;
import org.example.recipes.login.IdGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repo;
    private final IdGeneratorService idGenerator;

    public CommentServiceImpl(CommentRepository repo, IdGeneratorService idGenerator) {
        this.repo = repo;
        this.idGenerator = idGenerator;
    }

    @Override
    public int countComments(String recipeId) {
        return repo.countByRecipeId(recipeId);
    }

    @Override
    public List<Comment> listComments(String recipeId) {
        return repo.findByRecipeIdOrderByCreatedAtDesc(recipeId);
    }

    @Override
    @Transactional
    public void addComment(String userId, String recipeId, String content) {
        Comment c = new Comment();
        c.setCommentId(idGenerator.generateId());
        c.setUserId(userId);
        c.setRecipeId(recipeId);
        c.setContent(content);
        c.setCreatedAt(LocalDateTime.now());
        repo.save(c);
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        repo.deleteById(commentId);
    }
}