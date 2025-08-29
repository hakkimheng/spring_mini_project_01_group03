package org.example.miniproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.miniproject.exception.NotFoundException;
import org.example.miniproject.model.dto.request.ArticleRequest;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.ArticleResponse;
import org.example.miniproject.model.entity.Article;
import org.example.miniproject.model.entity.Category;
import org.example.miniproject.model.entity.CategoryArticle;
import org.example.miniproject.model.enums.ArticleProperties;
import org.example.miniproject.repository.ArticleRepository;
import org.example.miniproject.repository.CategoryArticleRepository;
import org.example.miniproject.repository.CategoryRepository;
import org.example.miniproject.service.ArticleService;
import org.example.miniproject.service.CategoryService;
import org.example.miniproject.service.UserService;
import org.example.miniproject.util.AuthUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.miniproject.model.dto.response.ApiResponseWithPagination.itemsAndPaginationResponse;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final CategoryArticleRepository categoryArticleRepository;

    @Override
    @Transactional
    public ArticleResponse createArticle(ArticleRequest request) {

        if (request.getCategoryIds().isEmpty()) {
            throw new NotFoundException("At least one category is required");
        }
        Article article = Article.builder().build();
        article.setTitle(request.getTitle().trim());
        article.setDescription(request.getDescription().trim());
        article.setAppUser(userService.getUser());

        List<CategoryArticle> categoryArticles = new ArrayList<>();
        for (Integer catId : request.getCategoryIds()) {
            Category categoryById = categoryService.getCategoryById(catId);
            categoryById.setAmountArticle(categoryById.getAmountArticle() + 1);
            categoryArticles.add(CategoryArticle.builder().
                    category(categoryById).
                    article(article).
                    build());
        }

        article.setCategory(categoryArticles);
        List<CategoryArticle> categoryArticlesList = categoryArticleRepository.saveAll(categoryArticles);
        return CategoryArticle.toResponse(categoryArticlesList);
    }

    @Override
    public Article getArticleById(Integer articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Article '" + articleId + "' Not Found"));
    }

    @Override
    public ApiResponseWithPagination<ArticleResponse> getAllArticles(Integer page, Integer size, ArticleProperties articleProperties, Sort.Direction direction) {
        Sort.Direction sortedDirection = direction == null ? Sort.Direction.ASC : direction;
        Sort sortBy = articleProperties == null ?
                Sort.by(sortedDirection, ArticleProperties.articleId.getProperty()) :
                Sort.by(sortedDirection, articleProperties.getProperty());
        Page<Article> pageOfArticle = articleRepository
                .findAll( PageRequest.of(page - 1, size, sortBy));
        return itemsAndPaginationResponse(pageOfArticle.map(Article::toResponse));
    }

    @Override
    @Transactional
    public ArticleResponse updateArticleById(Integer articleId, ArticleRequest request) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Article '" + articleId + "' Not Found"));
        //deduct old total articles
        List<Category> oldCatagoriesList = article.getCategory().stream().map(categoryArticle -> {
            Category category = categoryArticle.getCategory();
            category.setAmountArticle(categoryArticle.getCategory().getAmountArticle() - 1);
            return category;
        }).toList();
        categoryRepository.saveAll(oldCatagoriesList);

        //set new info for article
        article.setTitle(request.getTitle().trim());
        article.setDescription(request.getDescription().trim());
        article.setAppUser(userService.getUser());
        article.getCategory().clear();

        List<CategoryArticle> categoryArticles = new ArrayList<>();
        for (Integer catId : request.getCategoryIds()) {
            Category category = categoryRepository.findById(catId)
                    .orElseThrow(() -> new NotFoundException("Category '" + catId + "' Not Found"));
            category.setAmountArticle(category.getAmountArticle() + 1);
            CategoryArticle ca = new CategoryArticle();
            ca.setArticle(article);
            ca.setCategory(category);
            article.getCategory().add(ca);
            categoryArticles.add(ca);
        }
        List<CategoryArticle> saved = categoryArticleRepository.saveAll(categoryArticles);
        return CategoryArticle.toResponse(saved);
    }

    @Transactional
    public void deleteArticleById(Integer articleId) {
        Article a = getArticleById(articleId);

        List<Category> deletedArticles = a.getCategory().stream()
                .map(CategoryArticle::getCategory)
                .toList();

        articleRepository.delete(a);

        deletedArticles.forEach(c -> {
            c.setAmountArticle(c.getAmountArticle() - 1);
            categoryRepository.save(c);
        });
    }

}
