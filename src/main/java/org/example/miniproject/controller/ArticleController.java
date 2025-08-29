package org.example.miniproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.miniproject.model.dto.request.ArticleRequest;
import org.example.miniproject.model.dto.response.ApiResponse;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.ArticleResponse;
import org.example.miniproject.model.dto.response.BaseResponse;
import org.example.miniproject.model.entity.Article;
import org.example.miniproject.model.enums.ArticleProperties;
import org.example.miniproject.model.enums.CategoryProperties;
import org.example.miniproject.service.ArticleService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController extends BaseResponse {

    private final ArticleService articleService;

    @PostMapping
    @Operation(summary = "Create new article.can be used by only AUTHOR role")
    public ResponseEntity<ApiResponse<ArticleResponse>> addArticle(@RequestBody @Valid ArticleRequest request) {
        return responseEntity(true,"Article added successfully", HttpStatus.CREATED, articleService.createArticle(request));
    }
    @PreAuthorize("hasRole('AUTHOR')")
    @GetMapping("/{articleId}")
    @Operation(summary = "Get articles by article id.can be used by all roles")
    public ResponseEntity<ApiResponse<ArticleResponse>> getArticle(@PathVariable Integer articleId) {
        return responseEntity(true,"Get article successfully",HttpStatus.OK,articleService.getArticleById(articleId).toResponse());
    }
    @GetMapping
    @Operation(summary = "Get all articles.can be used by all roles")
    public ResponseEntity<ApiResponse<ApiResponseWithPagination<ArticleResponse>>> getAllArticles(@RequestParam(defaultValue = "1") @Positive Integer page,
                                                                                                  @RequestParam(defaultValue = "10") Integer size,
                                                                                                  @RequestParam(required = false) ArticleProperties articleProperties,
                                                                                                  @RequestParam(required = false) Sort.Direction direction) {
        return responseEntity(true,"get all articles successfully",HttpStatus.OK,articleService.getAllArticles(page,size,articleProperties,direction));
    }
    @PreAuthorize("hasRole('AUTHOR')")
    @PutMapping("/{articleId}")
    @Operation(summary = "Update new article.can be used by only AUTHOR role")
    public ResponseEntity<ApiResponse<ArticleResponse>> updateArticle(@PathVariable Integer articleId, @RequestBody @Valid ArticleRequest request) {
        return responseEntity(true,"Article have been updated",HttpStatus.OK,articleService.updateArticleById(articleId,request));
    }
    @PreAuthorize("hasRole('AUTHOR')")
    @DeleteMapping("/{articleId}")
    @Operation(summary = "Delete new article.can be used by only AUTHOR role")
    public ResponseEntity<ApiResponse<Void>> deleteArticle(@PathVariable Integer articleId) {
        articleService.deleteArticleById(articleId);
        return responseEntity(true,"Article have been deleted",HttpStatus.OK);
    }


}
