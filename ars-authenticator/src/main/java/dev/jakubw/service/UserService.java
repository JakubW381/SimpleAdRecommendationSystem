package dev.jakubw.service;

import dev.jakubw.domain.AuthUserDetails;
import dev.jakubw.dto.SignUpRequest;
import dev.jakubw.model.AuthDetailsEntity;
import dev.jakubw.model.Role;
import dev.jakubw.repository.AuthDetailsRepository;
import dev.jakubw.repository.AuthUserDetailsRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthDetailsRepository authUserDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthDetailsEntity saveUser(SignUpRequest request, String id) {
        AuthDetailsEntity details = AuthDetailsEntity.builder()
                .id(id)
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .authorities(Set.of(Role.USER))
                .build();

        return authUserDetailsRepository.save(details);
    }
}

