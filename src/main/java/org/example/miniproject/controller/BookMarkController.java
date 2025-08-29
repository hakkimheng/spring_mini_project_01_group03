package org.example.miniproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.miniproject.model.dto.response.ApiResponse;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.ArticleResponse;
import org.example.miniproject.model.dto.response.BaseResponse;
import org.example.miniproject.service.BookMarkService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/bookmarks")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Bookmark")
public class BookMarkController extends BaseResponse {

    private final BookMarkService bookMarkService;

    @Operation(summary = "Add bookmark on article")
    @PostMapping("/{articleId}")
    public ResponseEntity<ApiResponse<ArticleResponse>> addBookMarkByArticleId(@PathVariable("articleId") Integer id) {
        return responseEntity(true, "Added bookmark successfully", HttpStatus.OK, bookMarkService.markArticleById(id));
    }

    @Operation(summary = "Delete bookmark on article")
    @DeleteMapping("/{articleId}")
    public ResponseEntity<ApiResponse<Object>> removeBookMarkByArticleId(@PathVariable("articleId") Integer id) {
        bookMarkService.deleteMarkArticleById(id);
        return responseEntity(true, "Deleted bookmark successfully", HttpStatus.OK);
    }

    @Operation(summary = "Get all articles which has added bookmark by current user id")
    @GetMapping
    public ResponseEntity<ApiResponse<ApiResponseWithPagination<ArticleResponse>>> getAllArticleByCurrentIdOnBookMark(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Sort.Direction direction
    ) {
        return responseEntity(true, "Get all articles which has added bookmark successfully", HttpStatus.OK, bookMarkService.getAllBookMarksByCurrentUser(page, size, direction));
    }


}
