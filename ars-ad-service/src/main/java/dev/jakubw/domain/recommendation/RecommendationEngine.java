package dev.jakubw.domain.recommendation;

import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.model.AdTag;
import dev.jakubw.domain.port.out.ad.model.AdRecommendationCandidate;

import java.util.List;

public interface RecommendationEngine {
    RecommendationStrat strategy();
    List<AdRecommendationCandidate> recommend(List<AdTag> tags , int count, List<AdRecommendationCandidate> ads);
}
