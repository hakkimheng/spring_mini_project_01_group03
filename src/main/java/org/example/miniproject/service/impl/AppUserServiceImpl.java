package org.example.miniproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.miniproject.exception.BadRequestException;
import org.example.miniproject.model.dto.request.AppUserRequest;
import org.example.miniproject.model.dto.response.AppUserResponse;
import org.example.miniproject.repository.AppUserRepository;
import org.example.miniproject.service.AppUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findAppUserByEmail(email);
    }

    @Override
    public AppUserResponse register(AppUserRequest request) {
        if(appUserRepository.existsByUsername(request.getUsername())){
            throw new BadRequestException("Username '"+request.getUsername()+"' already exist.");
        }
        if(appUserRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email '"+request.getEmail()+"' already in use.");
        }
        if(appUserRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new BadRequestException("Phone number '"+request.getPhoneNumber()+"' already in use.");
        }
       return appUserRepository.save(request.toEntity(passwordEncoder)).toResponse();
    }



}
