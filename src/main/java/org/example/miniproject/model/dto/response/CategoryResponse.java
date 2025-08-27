package org.example.miniproject.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Integer categoryId;
    private String categoryName;
    private Integer amountOfArticles;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
