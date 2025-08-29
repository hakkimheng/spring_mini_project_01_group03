package org.example.miniproject.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    @NotBlank(message = "content can't be empty!")
    @NotNull(message = "content can't be empty!")
    @Size(min = 1 , message = "content can't be empty!")
    private String content;
}
