package dev.jakubw.domain.recommendation;

import dev.jakubw.domain.model.AdTag;
import dev.jakubw.domain.port.out.ad.model.AdRecommendationCandidate;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class WeightedRecommendationEngine implements RecommendationEngine {

    private static final long TAG_WEIGHT = 10_000L;
    private static final long REMAINING_IMPRESSIONS_WEIGHT = 1L;

    @Override
    public RecommendationStrat strategy() {
        return RecommendationStrat.WEIGHTED;
    }

    @Override
    public List<AdRecommendationCandidate> recommend(
            List<AdTag> tags,
            int count,
            List<AdRecommendationCandidate> ads
    ) {

        Set<AdTag> userTags = Set.copyOf(tags);

        return ads.stream()
                .filter(ad -> ad.todayImpressions() < ad.maxDayCount())
                .sorted(
                        Comparator.comparingLong(
                                (AdRecommendationCandidate ad) ->
                                        score(ad, userTags)
                        ).reversed()
                )
                .limit(count)
                .toList();
    }

    private long score(
            AdRecommendationCandidate ad,
            Set<AdTag> userTags
    ) {

        long matchedTags = ad.tags()
                .stream()
                .filter(userTags::contains)
                .count();
        long impressionsRemaining = ad.maxDayCount() - ad.todayImpressions();
        return (matchedTags * TAG_WEIGHT) + (impressionsRemaining * REMAINING_IMPRESSIONS_WEIGHT);
    }
}