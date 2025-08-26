package org.example.miniproject.service;

import org.example.miniproject.model.dto.request.UserRequest;
import org.example.miniproject.model.dto.response.AppUserResponse;

public interface UserService {
    AppUserResponse getUser();

    AppUserResponse updateUser(UserRequest authRequest);
}
