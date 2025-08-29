package org.example.miniproject.repository;

import org.example.miniproject.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByIdAndAppUserId(Integer categoryId, Integer currentUserId);
    Boolean existsByCategoryNameIgnoreCase(String name);
    Page<Category> findAllByAppUserId(Integer appUserId, Pageable pageable);

}
