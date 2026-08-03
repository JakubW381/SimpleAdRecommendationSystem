package dev.jakubw.config;

import dev.jakubw.adapter.out.persistance.AdUserPostgresAdapter;
import dev.jakubw.adapter.out.persistance.AdUserPostgresRepository;
import dev.jakubw.adapter.out.proto.GrpcRecommendationClientAdapter;
import dev.jakubw.application.handler.CreateUserHandler;
import dev.jakubw.application.handler.GetRecommendationHandler;
import dev.jakubw.application.handler.GetUserHandler;
import dev.jakubw.domain.port.out.AdUserRepositoryPort;
import dev.jakubw.domain.port.out.RecommendationPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // Adapter
    @Bean
    GrpcRecommendationClientAdapter grpcRecommendationClientAdapter(
            dev.jakubw.grpc.GetRecommendationServiceGrpc.GetRecommendationServiceBlockingStub stub
    ){
        return new GrpcRecommendationClientAdapter(stub);
    }

    @Bean
    AdUserPostgresAdapter adUserPostgresAdapter(
            AdUserPostgresRepository adUserPostgresRepository
    ){
        return new AdUserPostgresAdapter(adUserPostgresRepository);
    }

    // Handler
    @Bean
    public GetRecommendationHandler getRecommendationHandler(
            RecommendationPort recommendationPort,
            AdUserRepositoryPort adUserRepositoryPort
    ){
        return new GetRecommendationHandler(recommendationPort,adUserRepositoryPort);
    }

    @Bean
    public GetUserHandler getUserHandler(
            AdUserRepositoryPort adUserRepositoryPort
    ){
        return new GetUserHandler(adUserRepositoryPort);
    }

    @Bean
    public CreateUserHandler createUserHandler(
            AdUserRepositoryPort adUserRepositoryPort
    ){
        return new CreateUserHandler(adUserRepositoryPort);
    }
}
