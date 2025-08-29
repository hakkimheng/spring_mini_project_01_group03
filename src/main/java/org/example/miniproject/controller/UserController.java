package org.example.miniproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.miniproject.model.dto.request.UserRequest;
import org.example.miniproject.model.dto.response.ApiResponse;
import org.example.miniproject.model.dto.response.AppUserResponse;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Information")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get current user. can be used by all roles")
    public ResponseEntity<ApiResponse<AppUserResponse>> getUser() {

        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .timestamps(Instant.now())
                .success(true)
                .message("Get current user successfully")
                .payload(userService.getUser().toResponse())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    @Operation(summary = "Update current user can be use by all roles")
    public ResponseEntity<ApiResponse<AppUserResponse>> updateUser(@RequestBody @Valid UserRequest authRequest){

        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .timestamps(Instant.now())
                .success(true)
                .message("Updated current user successfully")
                .payload(userService.updateUser(authRequest))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
