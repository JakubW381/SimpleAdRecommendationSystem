package dev.jakubw.service;

import dev.jakubw.dto.ProviderSignUpRequest;
import dev.jakubw.dto.UserSignUpRequest;
import dev.jakubw.model.AuthDetailsEntity;
import dev.jakubw.model.Role;
import dev.jakubw.repository.AuthDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthDetailsRepository authUserDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthDetailsEntity saveUser(UserSignUpRequest request, String id) {
        AuthDetailsEntity details = createUserEntity(request,id);
        return authUserDetailsRepository.save(details);
    }

    @Transactional
    public AuthDetailsEntity saveProvider(ProviderSignUpRequest request, String id) {
        AuthDetailsEntity details = createProviderEntity(request,id);
        return authUserDetailsRepository.save(details);
    }

    private AuthDetailsEntity createUserEntity(UserSignUpRequest request, String id){
        return AuthDetailsEntity.builder()
                .id(id)
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .authorities(Set.of(Role.USER))
                .build();
    }

    private AuthDetailsEntity createProviderEntity(ProviderSignUpRequest request, String id){
        return AuthDetailsEntity.builder()
                .id(id)
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .authorities(Set.of(Role.PROVIDER))
                .build();
    }
}

