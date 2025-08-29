package org.example.miniproject.service;

import org.example.miniproject.model.dto.request.UserRequest;
import org.example.miniproject.model.dto.response.AppUserResponse;
import org.example.miniproject.model.entity.AppUser;

public interface UserService {
    AppUser getUser();

    AppUserResponse updateUser(UserRequest authRequest);
}
