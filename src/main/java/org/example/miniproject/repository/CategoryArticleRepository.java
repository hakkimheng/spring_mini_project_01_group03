package org.example.miniproject.repository;

import org.example.miniproject.model.entity.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryArticleRepository extends JpaRepository<CategoryArticle,Integer> {
    Integer countByCategory_Id(Integer categoryId);

    boolean existsByCategoryIdAndArticleId(int categoryId, int articleId);
    
    int countByArticleId(int i);
}
