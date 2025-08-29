package org.example.miniproject.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.miniproject.model.enums.Role;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserResponse {
    private Integer userId;
    private String username;
    private String email;
    private String phoneNumber;
    private Role role;
    private String address;
    private Instant createdAt;
    private Instant updatedAt;
}
