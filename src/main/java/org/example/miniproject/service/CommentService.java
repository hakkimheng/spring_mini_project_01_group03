package org.example.miniproject.service;

import org.example.miniproject.model.dto.request.CommentRequest;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.ArticleWithListCommentResponse;
import org.example.miniproject.model.dto.response.CommentResponse;
import org.example.miniproject.model.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(CommentRequest request, Integer articleId);

    ArticleWithListCommentResponse getAllCommentByArticleId(Integer articleId);

    Comment getCommentById(Integer commendId);

    Comment updateCommentById(Integer commentId, CommentRequest commentRequest);

    void deleteComment(Integer commentId);
}
