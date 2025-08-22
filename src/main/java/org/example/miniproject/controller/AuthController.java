package org.example.miniproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.miniproject.jwt.JwtService;
import org.example.miniproject.jwt.RefreshTokenService;
import org.example.miniproject.model.dto.request.AppUserRequest;
import org.example.miniproject.model.dto.request.AuthRequest;
import org.example.miniproject.model.dto.response.ApiResponse;
import org.example.miniproject.model.dto.response.AppUserResponse;
import org.example.miniproject.model.dto.response.AuthResponse;
import org.example.miniproject.model.dto.response.BaseResponse;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseResponse {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AppUserResponse>> register(@RequestBody AppUserRequest appUserRequest){
        return responseEntity(true,"Successfully authenticated", HttpStatus.OK,appUserService.register(appUserRequest));
    }

    @SneakyThrows
    @Operation(summary = "Login with email and password")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @jakarta.validation.Valid AuthRequest authRequest){
        authenticate(authRequest.getEmail(), authRequest.getPassword());
        UserDetails user = appUserService.loadUserByUsername(authRequest.getEmail());
        String access  = jwtService.generateToken(user);
        String refresh = refreshTokenService.create((AppUser) user);
        AuthResponse payload = AuthResponse.builder()
                .token(access)
                .refreshToken(refresh)
                .build();
        return responseEntity(true,"Login successfully", HttpStatus.OK, payload);
    }

    @Operation(summary = "Refresh access token")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestParam String refreshToken) {
        String email = refreshTokenService.verifyAndConsume(refreshToken);
        // 2. load user and rotate tokens
        UserDetails user = appUserService.loadUserByUsername(email);
        String newAccess  = jwtService.generateToken(user);

        AuthResponse payload = AuthResponse.builder()
                .token(newAccess)
                .refreshToken(refreshToken)
                .build();

        return responseEntity(true,"Token refreshed", HttpStatus.OK, payload);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
