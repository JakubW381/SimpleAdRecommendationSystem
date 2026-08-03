package dev.jakubw.adapter.in.dto;

import dev.jakubw.domain.model.AdTag;

import java.util.Set;

public record AdUserDto(
        String id,
        String username,
        String email,
        Set<AdTag>tags
) { }
