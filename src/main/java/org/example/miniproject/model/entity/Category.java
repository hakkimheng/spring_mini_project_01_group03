package org.example.miniproject.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.miniproject.model.dto.response.CategoryResponse;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    private String categoryName;
    private Integer amountArticle;
    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updateAt = LocalDateTime.now();


    public CategoryResponse categoryResponse(){
        return CategoryResponse.builder()
                .categoryId(this.categoryId)
                .amountOfArticles(this.amountArticle)
                .categoryName(this.categoryName)
                .createAt(this.createAt)
                .updateAt(this.updateAt)
                .build();
    }

}
