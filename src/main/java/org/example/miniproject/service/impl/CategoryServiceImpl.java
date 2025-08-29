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
import org.example.miniproject.repository.CategoryArticleRepository;
import org.example.miniproject.repository.CategoryRepository;
import org.example.miniproject.service.AppUserService;
import org.example.miniproject.service.CategoryService;
import org.example.miniproject.service.UserService;
import org.example.miniproject.util.AuthUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static org.example.miniproject.model.dto.response.ApiResponseWithPagination.itemsAndPaginationResponse;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        String checkNameCategory = categoryRequest.getCategoryName().toUpperCase(Locale.ROOT).trim();
        Boolean isDuplicateName = categoryRepository.existsByCategoryNameIgnoreCase(checkNameCategory);
        if(isDuplicateName) throw new BadRequestException("Category name already exists");

        return categoryRepository.save(categoryRequest.toEntity(userService.getUser())).categoryResponse();
    }

    @Override
    public ApiResponseWithPagination<CategoryResponse> getAllCategory(Integer page, Integer size, CategoryProperties categoryProperties, Sort.Direction direction) {
        Sort.Direction sortedDirection = direction == null ? Sort.Direction.ASC : direction;
        Sort sortBy = categoryProperties == null ?
                Sort.by(sortedDirection, CategoryProperties.categoryId.getProperty()) :
                Sort.by(sortedDirection, categoryProperties.getProperty());

        Integer currentUserId = AuthUtil.getUserIdOfCurrentUser();
        Page<Category> pageOfCategory = categoryRepository
                .findAllByAppUserId(currentUserId,(PageRequest.of(page - 1, size, sortBy)));
        return itemsAndPaginationResponse(pageOfCategory.map(Category::categoryResponse));
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        Integer currentUserId = AuthUtil.getUserIdOfCurrentUser();
        return categoryRepository.findByIdAndAppUserId(categoryId, currentUserId).orElseThrow(
                () -> new NotFoundException("Category with id '"+categoryId+"' not found"));
    }

    @Override
    public CategoryResponse updateCategoryById(Integer categoryId, CategoryRequest categoryRequest) {
        Category category = getCategoryById(categoryId);
        category.setCategoryName(categoryRequest.getCategoryName().trim());
        return categoryRepository.save(category).categoryResponse();
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        Category category = getCategoryById(categoryId);
        category.setAppUser(null);
        if (category.getAmountArticle()>0) {
            throw new BadRequestException("this category is already been used");
        }
        categoryRepository.delete(category);
    }


}
