package org.example.miniproject.repository;

import org.example.miniproject.model.entity.Article;
import org.example.miniproject.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByArticleId(Integer id);
    Optional<Comment> findByIdAndAppUserId(Integer id, Integer appUserId);

}
