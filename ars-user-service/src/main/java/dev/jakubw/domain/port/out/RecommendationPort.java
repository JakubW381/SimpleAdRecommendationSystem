package dev.jakubw.domain.port.out;

import dev.jakubw.domain.port.out.model.AdRecommendationCandidate;
import dev.jakubw.domain.port.out.model.GetRecommendationRequest;

import java.util.List;

public interface RecommendationPort {
    List<AdRecommendationCandidate> getRecommendations(GetRecommendationRequest request);
}
