package org.example.miniproject.service.impl;

import lombok.AllArgsConstructor;
import org.example.miniproject.exception.NotFoundException;
import org.example.miniproject.model.dto.request.CommentRequest;
import org.example.miniproject.model.dto.response.ArticleWithListCommentResponse;
import org.example.miniproject.model.dto.response.CommentResponse;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.model.entity.Article;
import org.example.miniproject.model.entity.Comment;
import org.example.miniproject.repository.AppUserRepository;
import org.example.miniproject.repository.ArticleRepository;
import org.example.miniproject.repository.CommentRepository;
import org.example.miniproject.service.CommentService;
import org.example.miniproject.util.AuthUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public ArticleWithListCommentResponse createComment(CommentRequest request , Integer articleId) {
        AppUser appUser = appUserRepository.findById(AuthUtil.getUserIdOfCurrentUser()).orElseThrow(
                () -> new NotFoundException("AppUser not found")
        );
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new NotFoundException("Article not found")
        );
        Comment comment = Comment.builder()
                .appUser(appUser)
                .article(article)
                .content(request.getContent().trim())
                .build();
        commentRepository.save(comment);
        List<CommentResponse> commentResponses = commentRepository.findAllByArticleId(articleId).stream()
                .map(Comment::toCommentResponse)
                .toList();
        return ArticleWithListCommentResponse.builder()
                .article(article.toResponse())
                .comment(commentResponses)
                .build();
    }

    @Override
    public ArticleWithListCommentResponse getAllCommentByArticleId(Integer articleId) {
        List<CommentResponse> commentResponses = commentRepository.findAllByArticleId(articleId).stream()
                .map(Comment::toCommentResponse)
                .toList();

        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new NotFoundException("Article not found")
        );
        return ArticleWithListCommentResponse.builder()
                .article(article.toResponse())
                .comment(commentResponses)
                .build();
    }

    @Override
    public Comment getCommentById(Integer commendId) {
        return commentRepository.findByIdAndAppUserId(commendId , AuthUtil.getUserIdOfCurrentUser()).orElseThrow(
                () -> new NotFoundException("This comment does not belong to you")
        );
    }

    @Override
    public Comment updateCommentById(Integer commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findByIdAndAppUserId(commentId , AuthUtil.getUserIdOfCurrentUser()).orElseThrow(
                () -> new NotFoundException("This comment does not belong to you")
        );
        comment.setContent(commentRequest.getContent());
        comment.setUpdatedAt(Instant.now());
        return  commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentRepository.findByIdAndAppUserId(commentId , AuthUtil.getUserIdOfCurrentUser()).orElseThrow(
                () -> new NotFoundException("This comment does not belong to you")
        );
        commentRepository.deleteById(commentId);
    }


}
