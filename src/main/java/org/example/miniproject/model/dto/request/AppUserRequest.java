package org.example.miniproject.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.model.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {
    @Schema(description = "Username", example = "John wick")
    @NotNull
    @NotBlank
    @Pattern(
            regexp = "^(?=.{3,20}$)(?!.*[._-]{2})[A-Za-z][A-Za-z0-9._-]*[A-Za-z0-9]$",
            message = "Username must be 3–20 chars, start with a letter, and can include letters, digits, '.', '_' or '-' without repeats."
    )
    private String username;

    @Schema(description = "User email", example = "wick@gmail.com")
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String email;

    @Schema(description = "User password", example = "123")
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String password;

    @Schema(description = "User phone number", example = "012 123 123")
    @NotNull
    @NotBlank
    @Size(max = 255)
    @Pattern(
            regexp = "^\\+?[1-9]\\d{7,14}$",
            message = "Phone must be 8–15 digits, optionally starting with '+', e.g. +85512345678."
    )
    private String phoneNumber;

    @Schema(description = "User address", example = "Phnom Penh, Cambodia")
    @NotNull
    @NotBlank
    @Size(min = 5, max = 255, message = "Address must be 5–255 characters.")
    private String address;
    @NotNull
    private Role role;

    public AppUser toEntity(PasswordEncoder passwordEncoder) {
        return AppUser.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber)
                .address(address)
                .role(role)
                .build();
    }
}
