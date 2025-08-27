package org.example.miniproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.miniproject.model.dto.request.CategoryRequest;
import org.example.miniproject.model.dto.response.ApiResponse;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.BaseResponse;
import org.example.miniproject.model.dto.response.CategoryResponse;
import org.example.miniproject.model.enums.CategoryProperties;
import org.example.miniproject.service.CategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CategoryController extends BaseResponse{

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('AUTHOR')")
    @Operation(summary = "Create new category.can be used by only AUTHOR role")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryResponse payload = categoryService.createCategory(categoryRequest);
        return responseEntity(true,"Created new category successfully", HttpStatus.CREATED,payload);
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @Operation(summary = "Get all categories.can be used by only AUTHOR role")
    @GetMapping
    public ResponseEntity<ApiResponse<ApiResponseWithPagination<CategoryResponse>>> getAllCategory(@RequestParam(defaultValue = "1") @Positive Integer page,
                                                                                                   @RequestParam(defaultValue = "10") Integer size,
                                                                                                   @RequestParam(required = false) CategoryProperties categoryProperties,
                                                                                                   @RequestParam(required = false) Sort.Direction direction){
        System.out.println("clicked");
        return responseEntity(true,"Get all categories successfully",HttpStatus.OK,categoryService.getAllCategory(page,size,categoryProperties,direction));
    }
}
