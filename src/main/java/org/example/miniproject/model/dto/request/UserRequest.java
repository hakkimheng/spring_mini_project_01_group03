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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

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
}
