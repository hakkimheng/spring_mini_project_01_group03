package org.example.miniproject.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.miniproject.model.dto.response.AppUserResponse;
import org.example.miniproject.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "app_users")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class AppUser extends BaseEntity implements UserDetails {
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;
    private String address;

    @Column(unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "appUser")
    private List<Category> category;

    @OneToMany(mappedBy = "appUser")
    private List<Article> article;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String r = role.getRoleName();
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + r),
                new SimpleGrantedAuthority(r)
        );
    }

    @Override
    public String getUsername() {return email;}

    public AppUserResponse toResponse() {
        return AppUserResponse.builder()
                .userId(getId())
                .createdAt(getCreatedAt())
                .username(username)
                .updatedAt(getUpdatedAt())
                .email(email)
                .role(role)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }
}
