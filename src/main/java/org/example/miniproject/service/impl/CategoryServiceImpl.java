package org.example.miniproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.miniproject.exception.BadRequestException;
import org.example.miniproject.exception.NotFoundException;
import org.example.miniproject.model.dto.request.CategoryRequest;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.CategoryResponse;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.model.entity.Category;
import org.example.miniproject.model.enums.CategoryProperties;
import org.example.miniproject.repository.AppUserRepository;
import org.example.miniproject.repository.CategoryRepository;
import org.example.miniproject.service.CategoryService;
import org.example.miniproject.util.AuthUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import static org.example.miniproject.model.dto.response.ApiResponseWithPagination.itemsAndPaginationResponse;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        AppUser user = appUserRepository.findById(AuthUtil.getUserIdOfCurrentUser()).orElseThrow(
                () -> new NotFoundException("User Not Found")
        );
        Category category = Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .appUser(user)
                .build();
        System.out.println("category : "+category);
        return categoryRepository.save(category).categoryResponse();
    }

    @Override
    public ApiResponseWithPagination<CategoryResponse> getAllCategory(Integer page, Integer size, CategoryProperties categoryProperties, Sort.Direction direction) {
        Sort.Direction sortedDirection = direction == null ? Sort.Direction.ASC : direction;
        Sort sortBy = categoryProperties == null ?
                Sort.by(sortedDirection,CategoryProperties.categoryId.getProperty()):
                Sort.by(sortedDirection, categoryProperties.getProperty());

        Page<Category> pageOfCategory = categoryRepository
                .findAll(PageRequest.of(page-1,size,sortBy));

        Page<CategoryResponse> pageOfResponse = pageOfCategory.map(Category::categoryResponse);
        return itemsAndPaginationResponse(pageOfResponse);
    }

    @Override
    public CategoryResponse getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                ()-> new NotFoundException("Category id not found")).categoryResponse();
    }

    @Override
    public CategoryResponse updateCategoryById(Integer categoryId, CategoryRequest categoryRequest) {
        Long currentUser = AuthUtil.getUserIdOfCurrentUser();
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new NotFoundException("Category id not found"));
        if(!category.getAppUser().getId().equals(currentUser)){
            throw new BadRequestException("You are not owner of this category");
        }
        category.setCategoryName(categoryRequest.getCategoryName());
        return categoryRepository.save(category).categoryResponse();
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        Long currentUser = AuthUtil.getUserIdOfCurrentUser();
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new NotFoundException("Category id not found"));
        if(!category.getAppUser().getId().equals(currentUser)){
            throw new BadRequestException("You are not owner of this category");
        }
        categoryRepository.delete(category);
    }


}
