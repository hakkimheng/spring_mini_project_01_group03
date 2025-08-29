package org.example.miniproject.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.miniproject.model.dto.response.ArticleWithCommentResponse;
import org.example.miniproject.model.dto.response.CommentResponse;


@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;


    public CommentResponse toCommentResponse() {
        return CommentResponse.builder()
                .content(this.content)
                .commentId(this.getId())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .userId(this.appUser.getId())
                .build();
    }

    public ArticleWithCommentResponse toArticleAndCommentResponse() {
        return ArticleWithCommentResponse.builder()
                .comment(this.toCommentResponse())
                .article(this.article.toResponse())
                .build();
    }

}
