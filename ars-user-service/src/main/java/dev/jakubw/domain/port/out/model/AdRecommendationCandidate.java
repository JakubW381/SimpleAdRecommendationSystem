package dev.jakubw.domain.port.out.model;

import dev.jakubw.domain.model.AdTag;

import java.util.Set;

public record AdRecommendationCandidate(
        String id,
        String name,
        String adUrl
) {
}
