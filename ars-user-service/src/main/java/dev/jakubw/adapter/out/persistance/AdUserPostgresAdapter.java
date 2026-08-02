package dev.jakubw.adapter.out.persistance;

import dev.jakubw.config.exception.SignUpException;
import dev.jakubw.domain.model.AdUser;
import dev.jakubw.domain.port.out.AdUserRepositoryPort;
import dev.jakubw.domain.port.out.model.CreateUserPortRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
public class AdUserPostgresAdapter implements AdUserRepositoryPort {

    private final AdUserPostgresRepository adUserPostgresRepository;

    @Override
    @Transactional(readOnly = true)
    public AdUser findUserById(String userId) {
        AdUserEntity entity = adUserPostgresRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id:" + userId + " not found."));
        return new AdUser(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getTags()
        );
    }

    @Override
    @Transactional
    public AdUser createUser(CreateUserPortRequest request) {
        try {
            AdUserEntity entity = AdUserEntity.builder()
                    .id(request.id())
                    .username(request.username())
                    .email(request.email())
                    .tags(request.tags())
                    .build();

            AdUserEntity saved = adUserPostgresRepository.saveAndFlush(entity);

            return new AdUser(
                    saved.getId(),
                    saved.getUsername(),
                    saved.getEmail(),
                    saved.getTags()
            );
        } catch (Exception e) {
            throw new SignUpException(request.email(), e.getCause());
        }
    }
}
