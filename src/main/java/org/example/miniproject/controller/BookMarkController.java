package org.example.miniproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.miniproject.model.dto.response.ApiResponse;
import org.example.miniproject.model.dto.response.ArticleResponse;
import org.example.miniproject.model.dto.response.BaseResponse;
import org.example.miniproject.service.BookMarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/bookmarks")
@SecurityRequirement(name = "bearerAuth")
public class BookMarkController extends BaseResponse {

    private final BookMarkService bookMarkService;

    @Operation(summary = "Add bookmark on article")
    @PostMapping("/{articleId}")
    public ResponseEntity<ApiResponse<ArticleResponse>> register(@PathVariable("articleId") Integer id){
        return responseEntity(true,"Added bookmark successfully", HttpStatus.OK,bookMarkService.markArticleById(id));
    }


}
