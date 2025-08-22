package org.example.miniproject.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum Role {
    AUTHOR("ROLE_AUTHOR"),
    READER("ROLE_NAME");
    private final String roleName;
}
