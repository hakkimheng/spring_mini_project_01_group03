package org.example.miniproject.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArticleProperties {
    articleId("id"),
    articleName("title"),
    createdAt("createdAt");

    private final String property;
}
