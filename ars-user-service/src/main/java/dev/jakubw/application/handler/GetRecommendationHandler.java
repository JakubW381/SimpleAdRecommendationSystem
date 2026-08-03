package dev.jakubw.application.handler;

import dev.jakubw.adapter.out.proto.GrpcRecommendationClientAdapter;
import dev.jakubw.domain.model.AdUser;
import dev.jakubw.domain.port.in.GetRecommendationQry;
import dev.jakubw.domain.port.out.AdUserRepositoryPort;
import dev.jakubw.domain.port.out.RecommendationPort;
import dev.jakubw.domain.port.out.model.AdRecommendationCandidate;
import dev.jakubw.domain.port.out.model.GetRecommendationRequest;
import dev.jakubw.domain.port.out.model.RecommendationStrat;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetRecommendationHandler implements GetRecommendationQry {

    private final RecommendationPort recommendationPort;
    private final AdUserRepositoryPort adUserRepositoryPort;
    @Override
    public List<AdRecommendationCandidate> execute(String userId, int count, RecommendationStrat strat) {
        AdUser user = adUserRepositoryPort.findUserById(userId);
        return recommendationPort.getRecommendations(new GetRecommendationRequest(count,user.getTags(),strat));
    }
}
