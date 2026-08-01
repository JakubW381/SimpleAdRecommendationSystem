package dev.jakubw.domain.port.out;

import dev.jakubw.domain.model.AdUser;

import java.util.Optional;

public interface AdUserRepositoryPort {
    AdUser findUserById(String userId);
}
