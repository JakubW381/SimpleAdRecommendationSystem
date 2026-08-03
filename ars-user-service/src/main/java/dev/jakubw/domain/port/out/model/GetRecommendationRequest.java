package dev.jakubw.domain.port.out.model;

import dev.jakubw.domain.model.AdTag;

import java.util.Set;

public record GetRecommendationRequest(

        int count,
        Set<AdTag> tags,
        RecommendationStrat strat
) {
}
