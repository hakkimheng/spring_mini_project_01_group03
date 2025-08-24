package org.example.miniproject.util;

import org.example.miniproject.model.entity.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static Long getUserIdOfCurrentUser() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long userId = appUser.getId();
        return userId;
    }

}
