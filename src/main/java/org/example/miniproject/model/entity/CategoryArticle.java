package org.example.miniproject.model.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.miniproject.model.dto.response.ArticleResponse;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category_article")
public class CategoryArticle extends BaseEntity{

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "article_id")
    private Article article;

    public static ArticleResponse toResponse(List<CategoryArticle> categoryArticlesList) {
        Article article = categoryArticlesList.getFirst().getArticle();
        return ArticleResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .description(article.getDescription())
                .appUserId(article.getAppUser().getId())
                .categories(categoryArticlesList.stream().map(categoryArticle -> categoryArticle.getCategory().getCategoryName()).toList())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }
}
