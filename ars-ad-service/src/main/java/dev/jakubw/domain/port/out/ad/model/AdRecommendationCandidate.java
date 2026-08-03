package dev.jakubw.domain.port.out.ad.model;

import dev.jakubw.domain.model.AdTag;

import java.util.Set;

public record AdRecommendationCandidate(
        String id,
        String name,
        String adUrl,
        Set<AdTag> tags,
        Long maxDayCount,
        Long todayImpressions
) {
    public AdRecommendationCandidate(
            String id,
            String name,
            String adUrl,
            Set<AdTag> tags,
            Long maxDayCount,
            int todayImpressions
    ) {
        this(
                id,
                name,
                adUrl,
                tags,
                maxDayCount,
                (long) todayImpressions
        );
    }
}
