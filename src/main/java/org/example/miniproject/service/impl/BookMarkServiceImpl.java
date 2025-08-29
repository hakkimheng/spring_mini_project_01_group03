package org.example.miniproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.miniproject.exception.BadRequestException;
import org.example.miniproject.exception.NotFoundException;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.ArticleResponse;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.model.entity.Article;
import org.example.miniproject.model.entity.BookMark;
import org.example.miniproject.model.entity.Category;
import org.example.miniproject.model.enums.CategoryProperties;
import org.example.miniproject.repository.AppUserRepository;
import org.example.miniproject.repository.ArticleRepository;
import org.example.miniproject.repository.BookMarkRepository;
import org.example.miniproject.service.BookMarkService;
import org.example.miniproject.util.AuthUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.miniproject.model.dto.response.ApiResponseWithPagination.itemsAndPaginationResponse;

@Service
@RequiredArgsConstructor
public class BookMarkServiceImpl implements BookMarkService {

    private final AppUserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final BookMarkRepository bookMarkRepository;


    @Override
    @Transactional
    public ArticleResponse markArticleById(Integer id) {
        Integer currentUserId = AuthUtil.getUserIdOfCurrentUser();

        if (bookMarkRepository.existsByArticleIdAndAppUserId(id, currentUserId))
            throw new BadRequestException("You have already bookmarked this article.");

        BookMark bookMark = new BookMark();
        AppUser appUser = userRepository.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundException("Article '" + id + "' Not Found"));

        bookMark.setArticle(article);
        bookMark.setAppUser(appUser);

        bookMarkRepository.save(bookMark);

        return article.toResponse();
    }

    @Override
    public void deleteMarkArticleById(Integer id) {
        Integer currentUserId = AuthUtil.getUserIdOfCurrentUser();
        BookMark bookMark = bookMarkRepository.findByArticleIdAndAppUserId(id, currentUserId).orElseThrow(() -> new BadRequestException("No bookmark found for article with id: " + id));
        bookMarkRepository.delete(bookMark);
    }

    @Override
    public ApiResponseWithPagination<ArticleResponse> getAllBookMarksByCurrentUser(Integer page, Integer size, Sort.Direction direction) {
        Sort.Direction sortedDirection = direction == null ? Sort.Direction.ASC : direction;
        Sort sortBy = Sort.by(sortedDirection,"id");
        Integer currentUserId = AuthUtil.getUserIdOfCurrentUser();
        Page<BookMark> pagesOfBookMarks = bookMarkRepository
                .findAllByAppUserId(currentUserId, (PageRequest.of(page - 1, size, sortBy)));
        return itemsAndPaginationResponse(pagesOfBookMarks.map(BookMark::getArticle).map(Article::toResponse));
    }
}
