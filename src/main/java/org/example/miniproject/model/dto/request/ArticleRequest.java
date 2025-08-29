package org.example.miniproject.model.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleRequest {
    @NotBlank(message = "Article title cannot be Empty")
    @Size(message = "title cannot be longer than 20 characters",max = 20)
    private String title;
    private String description;
    private Set<Integer> categoryIds;

}
