package org.example.miniproject.service;

import jakarta.validation.constraints.Positive;
import org.example.miniproject.model.dto.response.ApiResponse;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.ArticleResponse;
import org.example.miniproject.model.entity.Article;
import org.springframework.data.domain.Sort;

public interface BookMarkService {

    ArticleResponse markArticleById(Integer id);
    ApiResponse<?> deleteMarkArticleById(Integer id);
    ApiResponseWithPagination<ArticleResponse> getAllBookMarksByCurrentUser(@Positive Integer page, Integer size, Sort.Direction direction);

}
