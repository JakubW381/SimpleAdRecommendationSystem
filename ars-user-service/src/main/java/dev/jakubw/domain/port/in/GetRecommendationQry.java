package dev.jakubw.domain.port.in;

import dev.jakubw.domain.port.out.model.AdRecommendationCandidate;
import dev.jakubw.domain.port.out.model.RecommendationStrat;

import java.util.List;

public interface GetRecommendationQry {
    List<AdRecommendationCandidate> execute(String userId, int count, RecommendationStrat strat);
}
