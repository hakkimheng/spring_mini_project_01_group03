package org.example.miniproject.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.model.entity.Category;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    @NotBlank(message = "Category name cannot be Empty")
    @Size(message = "Name cannot be longer than 20 characters",max = 20)
    private String categoryName;

    public Category toEntity(AppUser appUser) {
        return Category.builder()
                .categoryName(this.categoryName)
                .appUser(appUser)
                .build();
    }
}
