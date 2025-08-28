package org.example.miniproject.service;

import jakarta.validation.constraints.Positive;
import org.example.miniproject.model.dto.request.CategoryRequest;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.CategoryResponse;
import org.example.miniproject.model.enums.CategoryProperties;
import org.springframework.data.domain.Sort;


public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);

    ApiResponseWithPagination<CategoryResponse> getAllCategory(@Positive Integer page, Integer size, CategoryProperties categoryProperties, Sort.Direction direction);

    CategoryResponse getCategoryById(Integer categoryId);

    CategoryResponse updateCategoryById(Integer categoryId, CategoryRequest categoryRequest);

    void deleteCategoryById(Integer categoryId);
}
