package dev.jakubw.domain.port.out;

import dev.jakubw.domain.model.AdUser;
import dev.jakubw.domain.port.out.model.CreateUserPortRequest;

public interface AdUserRepositoryPort {
    AdUser findUserById(String userId);
    AdUser createUser(CreateUserPortRequest request);
}
