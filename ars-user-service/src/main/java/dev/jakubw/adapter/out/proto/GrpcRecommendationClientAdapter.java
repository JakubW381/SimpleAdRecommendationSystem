package dev.jakubw.adapter.out.proto;

import dev.jakubw.config.exception.RecommendationException;
import dev.jakubw.domain.port.out.RecommendationPort;
import dev.jakubw.domain.port.out.model.AdRecommendationCandidate;
import dev.jakubw.domain.port.out.model.GetRecommendationRequest;
import dev.jakubw.grpc.GetRecommendationResponse;
import dev.jakubw.grpc.GetRecommendationServiceGrpc.GetRecommendationServiceBlockingStub;
import dev.jakubw.grpc.RpcAdTag;
import dev.jakubw.grpc.RpcRecommendationStrat;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GrpcRecommendationClientAdapter implements RecommendationPort {

    private final GetRecommendationServiceBlockingStub stub;

    @Override
    public List<AdRecommendationCandidate> getRecommendations(GetRecommendationRequest request) {
        try{
            List<RpcAdTag> rpcTags = request.tags().stream().map(tag -> RpcAdTag.valueOf(tag.name())).toList();

            dev.jakubw.grpc.GetRecommendationRequest rpcRequest = dev.jakubw.grpc.GetRecommendationRequest
                    .newBuilder()
                    .setCount(request.count())
                    .setStrat(RpcRecommendationStrat.valueOf(request.strat().name()))
                    .addAllTags(rpcTags)
                    .build();

            GetRecommendationResponse recommendations = stub.getRecommendation(rpcRequest);
            return recommendations.getRecommendationsList()
                    .stream().map( response ->
                            new AdRecommendationCandidate(response.getId(), response.getName(), response.getAdUrl()))
                    .toList();
        } catch (StatusRuntimeException e) {
            throw new RecommendationException(e.getMessage(),e.getCause());
        }
    }
}
