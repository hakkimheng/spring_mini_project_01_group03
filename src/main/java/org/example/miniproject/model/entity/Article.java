package org.example.miniproject.model.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.miniproject.model.dto.response.ArticleResponse;
import org.example.miniproject.model.dto.response.BaseResponse;
import org.example.miniproject.model.dto.response.CategoryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "articles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"category", "appUser"})
@Builder
@AttributeOverride(name = "id", column = @Column(name = "article_id"))
public class Article extends BaseEntity {

    private String title;
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryArticle> category = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "article")
    private List<Comment> comment;

    public ArticleResponse toResponse() {
        List<String> categoryNames =
                (category == null ? List.<CategoryArticle>of() : category).stream()
                        .map(CategoryArticle::getCategory)
                        .map(Category::getCategoryName)
                        .toList();
        return ArticleResponse.builder()
                .articleId(getId())
                .title(title)
                .description(description)
                .appUserId(appUser.getId())
                .categories(categoryNames)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
