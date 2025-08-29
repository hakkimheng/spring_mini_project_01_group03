package org.example.miniproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.miniproject.exception.BadRequestException;
import org.example.miniproject.exception.NotFoundException;
import org.example.miniproject.model.dto.request.UserRequest;
import org.example.miniproject.model.dto.response.AppUserResponse;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.repository.AppUserRepository;
import org.example.miniproject.repository.UserRepository;
import org.example.miniproject.service.UserService;
import org.example.miniproject.util.AuthUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public Integer userId() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appUser.getId();
    }

    @Override
    public AppUser getUser() {
        return userRepository.findById(userId()).orElseThrow(()->new NotFoundException("User not found"));
    }

    @Override
    public AppUserResponse updateUser(UserRequest authRequest) {
        AppUser user= getUser();
        if(userRepository.existsByPhoneNumberAndIdNot(authRequest.getPhoneNumber(), AuthUtil.getUserIdOfCurrentUser())){
            throw new BadRequestException("This phone number is already in use.");
        }
        user.setPhoneNumber(authRequest.getPhoneNumber());
        user.setAddress(authRequest.getAddress());
        return userRepository.save(user).toResponse();
    }
}
