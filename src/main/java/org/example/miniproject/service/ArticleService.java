package org.example.miniproject.service;

import jakarta.validation.constraints.Positive;
import org.example.miniproject.model.dto.request.ArticleRequest;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.ArticleResponse;
import org.example.miniproject.model.entity.Article;
import org.example.miniproject.model.enums.ArticleProperties;
import org.springframework.data.domain.Sort;

public interface ArticleService {
    ArticleResponse createArticle(ArticleRequest request);

    Article getArticleById(Integer articleId);

    ApiResponseWithPagination<ArticleResponse> getAllArticles(@Positive Integer page, Integer size, ArticleProperties articleProperties, Sort.Direction direction);

    ArticleResponse updateArticleById(Integer articleId, ArticleRequest request);

    void deleteArticleById(Integer articleId);
}
