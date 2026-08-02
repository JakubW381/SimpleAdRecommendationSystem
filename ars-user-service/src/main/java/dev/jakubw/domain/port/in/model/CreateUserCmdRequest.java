package dev.jakubw.domain.port.in.model;

import dev.jakubw.domain.model.AdTag;

import java.util.Set;

public record CreateUserCmdRequest(
        String username,
        String email,
        Set<AdTag> tags
) {
}
