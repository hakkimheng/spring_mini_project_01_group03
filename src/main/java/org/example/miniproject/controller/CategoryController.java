package org.example.miniproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Category")
public class CategoryController extends BaseResponse{

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('AUTHOR')")
    @Operation(summary = "Create new category.can be used by only AUTHOR role")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest categoryRequest){
        return responseEntity(true,"Created new category successfully", HttpStatus.CREATED,categoryService.createCategory(categoryRequest));
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @Operation(summary = "Get all categories.can be used by only AUTHOR role")
    @GetMapping
    public ResponseEntity<ApiResponse<ApiResponseWithPagination<CategoryResponse>>> getAllCategory(@RequestParam(defaultValue = "1") @Positive Integer page,
                                                                                                   @RequestParam(defaultValue = "10") Integer size,
                                                                                                   @RequestParam(required = false) CategoryProperties categoryProperties,
                                                                                                   @RequestParam(required = false) Sort.Direction direction){
        return responseEntity(true,"Get all categories successfully",HttpStatus.OK,categoryService.getAllCategory(page,size,categoryProperties,direction));
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @Operation(summary = "Get category by id.can be used by only AUTHOR role")
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@RequestParam Integer categoryId){
        return responseEntity(true,"Get category by id successfully",HttpStatus.OK,categoryService.getCategoryById(categoryId).categoryResponse());
    }


    @PreAuthorize("hasRole('AUTHOR')")
    @Operation(summary = "Update category by id.can be used by only AUTHOR role")
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategoryById(@RequestParam Integer categoryId, @Valid @RequestBody CategoryRequest categoryRequest){
        return responseEntity(true,"update category by id successfully",HttpStatus.OK,categoryService.updateCategoryById(categoryId,categoryRequest));
    }


    @PreAuthorize("hasRole('AUTHOR')")
    @Operation(summary = "Delete category by id.can be used by only AUTHOR role")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategoryById(@RequestParam Integer categoryId){
        categoryService.deleteCategoryById(categoryId);
        return responseEntity(true,"Deleted category successfully",HttpStatus.OK,null);
    }
}
