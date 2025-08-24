package org.example.miniproject.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotNull
    @NotBlank
    @Size(max = 254)
    @Schema(
            description = "User email",
            example = "wick@gmail.com"
    )
    private String email;
    @NotNull
    @NotBlank
    @Schema(
            description = "User password",
            example = "123"
    )
    private String password;
}
