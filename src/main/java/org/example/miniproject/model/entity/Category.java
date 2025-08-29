package org.example.miniproject.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.miniproject.model.dto.response.CategoryResponse;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@Builder
@AttributeOverride(name = "id", column = @Column(name = "category_id"))
public class Category extends BaseEntity{

    @Column(unique = true)
    private String categoryName;

    @Builder.Default
    private Integer amountArticle = 0;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryArticle> categoryArticles;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private AppUser appUser;


    public CategoryResponse categoryResponse(){
        return CategoryResponse.builder()
                .categoryId(getId())
                .amountOfArticles(this.amountArticle)
                .categoryName(this.categoryName)
                .createAt(this.getCreatedAt())
                .updateAt(this.getUpdatedAt())
                .build();
    }

}
