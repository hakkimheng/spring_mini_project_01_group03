package org.example.miniproject.service;

import org.example.miniproject.model.dto.request.AppUserRequest;
import org.example.miniproject.model.dto.response.AppUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    AppUserResponse register(AppUserRequest appUserRequest);
}
