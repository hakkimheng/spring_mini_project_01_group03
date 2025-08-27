package org.example.miniproject.repository;

import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.util.AuthUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    UserDetails findAppUserByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String username);

    boolean existsByPhoneNumber(String phoneNumber);
}
