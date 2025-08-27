package org.example.miniproject.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum CategoryProperties {
    categoryId("categoryId"),
    categoryName("categoryName"),
    createAt("createAt");

    private final String property;
}
