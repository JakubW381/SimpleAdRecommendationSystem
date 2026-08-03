package dev.jakubw.config;

import dev.jakubw.grpc.GetRecommendationServiceGrpc;
import dev.jakubw.grpc.GetRecommendationServiceGrpc.GetRecommendationServiceBlockingStub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcClientConfig {

    @Bean
    GetRecommendationServiceBlockingStub getRecommendationServiceBlockingStub(GrpcChannelFactory factory){
        return GetRecommendationServiceGrpc.newBlockingStub(factory.createChannel("localhost:9081"));
    }
}
