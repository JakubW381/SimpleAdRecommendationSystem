package dev.jakubw.application.handler.ad;

import dev.jakubw.domain.model.AdTag;
import dev.jakubw.domain.port.in.ad.GetRecommendedAdsQry;
import dev.jakubw.domain.port.out.ad.AdRepositoryPort;
import dev.jakubw.domain.port.out.ad.model.AdRecommendationCandidate;
import dev.jakubw.domain.recommendation.RecommendationEngine;
import dev.jakubw.domain.recommendation.RecommendationStrat;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GetAdRecommendationHandler implements GetRecommendedAdsQry {

    private final AdRepositoryPort adRepositoryPort;
    private final Map<RecommendationStrat, RecommendationEngine> engines;


    @Override
    public List<AdRecommendationCandidate> execute(List<AdTag> tags, int count, RecommendationStrat strategy) {
        List<AdRecommendationCandidate> ads = adRepositoryPort.getRecommendationCandidates();
        return engines.get(strategy).recommend(tags, count, ads);
    }
}
