package org.example.miniproject.repository;

import org.example.miniproject.model.entity.Article;
import org.example.miniproject.model.entity.BookMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark,Integer> {
    Boolean existsByArticleIdAndAppUserId(Integer id,Integer userId);
    Optional<BookMark> findByArticleIdAndAppUserId(Integer id,Integer userId);
    Page<BookMark> findAllByAppUserId(Integer userId, Pageable pageable);
}
