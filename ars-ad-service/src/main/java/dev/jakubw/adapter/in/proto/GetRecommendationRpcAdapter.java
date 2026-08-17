package dev.jakubw.adapter.in.proto;

import dev.jakubw.config.exception.RecommendationException;
import dev.jakubw.domain.model.AdTag;
import dev.jakubw.domain.port.in.ad.GetRecommendedAdsQry;
import dev.jakubw.domain.port.out.ad.model.AdRecommendationCandidate;
import dev.jakubw.domain.recommendation.RecommendationStrat;
import dev.jakubw.grpc.GetRecommendation;
import dev.jakubw.grpc.GetRecommendationRequest;
import dev.jakubw.grpc.GetRecommendationResponse;
import dev.jakubw.grpc.GetRecommendationServiceGrpc.GetRecommendationServiceImplBase;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class GetRecommendationRpcAdapter extends GetRecommendationServiceImplBase {

    private final GetRecommendedAdsQry query;

    @Override
    public void getRecommendation(GetRecommendationRequest request, StreamObserver<GetRecommendationResponse> responseObserver) {
        try{
            List<AdTag> tags = request.getTagsList().stream().map(tag -> AdTag.valueOf(tag.name())).toList();
            List<AdRecommendationCandidate> candidates = query.execute(tags, (int) request.getCount(), RecommendationStrat.valueOf(request.getStrat().name()));
            GetRecommendationResponse response = GetRecommendationResponse.newBuilder()
                    .addAllRecommendations(candidates.stream().map( rec ->
                            GetRecommendation.newBuilder()
                            .setId(rec.id())
                            .setName(rec.name())
                            .setAdUrl(rec.adUrl())
                            .build()).toList())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (StatusRuntimeException e){
            throw new RecommendationException(e.getMessage(),e.getCause());
        }
    }
}
