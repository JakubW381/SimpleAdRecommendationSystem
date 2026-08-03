package dev.jakubw.domain.port.in.ad;

import dev.jakubw.domain.model.AdTag;
import dev.jakubw.domain.port.out.ad.model.AdRecommendationCandidate;
import dev.jakubw.domain.recommendation.RecommendationStrat;

import java.util.List;

public interface GetRecommendedAdsQry {
    List<AdRecommendationCandidate> execute(List<AdTag> tags, int count, RecommendationStrat strategy);
}
