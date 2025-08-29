package org.example.miniproject.repository;


import org.example.miniproject.model.dto.response.ArticleResponse;
import org.example.miniproject.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
    Optional<Article> findByIdAndAppUserId(Integer id, Integer appUserId);

    Page<Article> findAllByAppUserId(Integer appUserId, Pageable pageable);
}
