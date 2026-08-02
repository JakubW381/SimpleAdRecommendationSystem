package dev.jakubw.domain.port.out.model;

import dev.jakubw.domain.model.AdTag;

import java.util.Set;

public record CreateUserPortRequest(
        String id,
        String username,
        String email,
        Set<AdTag> tags
) {
}
