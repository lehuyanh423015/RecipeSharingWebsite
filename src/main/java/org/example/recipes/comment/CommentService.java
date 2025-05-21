package org.example.recipes.comment;

import org.example.recipes.entity.Comment;

import java.util.List;

public interface CommentService {
    int countComments(String recipeId);
    List<Comment> listComments(String recipeId);
    void addComment(String userId, String recipeId, String content);
    void deleteComment(String commentId);
}