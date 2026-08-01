package dev.jakubw.adapter.in.rest.dto;

import dev.jakubw.domain.model.AdTags;

import java.util.Set;

public record AdUserDto(
        String id,
        String username,
        String email,
        Set<AdTags>tags
) { }
