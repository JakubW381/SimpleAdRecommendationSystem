package dev.jakubw.adapter.out.persistance;

import dev.jakubw.domain.model.AdUser;
import dev.jakubw.domain.port.out.AdUserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AdUserPostgresAdapter implements AdUserRepositoryPort {

    private final AdUserPostgresRepository adUserPostgresRepository;

    @Override
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
}
