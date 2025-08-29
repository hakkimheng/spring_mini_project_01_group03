package org.example.miniproject.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.miniproject.model.entity.Article;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleWithCommentResponse {
    private ArticleResponse article;
    private CommentResponse comment;
}
