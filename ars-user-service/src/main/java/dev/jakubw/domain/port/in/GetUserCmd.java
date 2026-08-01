package dev.jakubw.domain.port.in;

import dev.jakubw.domain.model.AdUser;

import java.util.Optional;

public interface GetUserCmd {
    AdUser execute(String userId);
}
