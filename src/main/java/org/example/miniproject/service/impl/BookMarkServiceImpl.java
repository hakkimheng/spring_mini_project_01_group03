package org.example.miniproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.miniproject.exception.ArticleAlreadyExisted;
import org.example.miniproject.exception.NotFoundException;
import org.example.miniproject.model.dto.response.ApiResponse;
import org.example.miniproject.model.dto.response.ApiResponseWithPagination;
import org.example.miniproject.model.dto.response.ArticleResponse;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.model.entity.Article;
import org.example.miniproject.model.entity.BookMark;
import org.example.miniproject.repository.AppUserRepository;
import org.example.miniproject.repository.ArticleRepository;
import org.example.miniproject.repository.BookMarkRepository;
import org.example.miniproject.service.BookMarkService;
import org.example.miniproject.util.AuthUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

        if(bookMarkRepository.existsByArticleIdAndAppUserId(id,currentUserId)) throw new ArticleAlreadyExisted("You have already bookmarked this article.");

        BookMark bookMark = new BookMark();
        AppUser appUser = userRepository.findById(currentUserId).orElseThrow(()-> new NotFoundException("User not found"));
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundException("Article '" + id + "' Not Found"));

        bookMark.setArticle(article);
        bookMark.setAppUser(appUser);

        bookMarkRepository.save(bookMark);

        return article.toResponse();
    }

    @Override
    public ApiResponse<?> deleteMarkArticleById(Integer id) {
        return null;
    }

    @Override
    public ApiResponseWithPagination<ArticleResponse> getAllBookMarksByCurrentUser(Integer page, Integer size, Sort.Direction direction) {
        return null;
    }
}
