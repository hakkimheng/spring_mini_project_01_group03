package org.example.miniproject.model.dto.response;


import lombok.*;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.model.entity.Category;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponse {
    private Integer articleId;
    private String title;
    private String description;
    private Integer appUserId;
    private List<String> categories;
    private Instant createdAt;
    private Instant updatedAt;
}
