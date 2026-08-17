package dev.jakubw.config;

import dev.jakubw.adapter.in.proto.GetRecommendationRpcAdapter;
import dev.jakubw.adapter.in.scheduling.PersistImpressionsSchedulerImpl;
import dev.jakubw.adapter.out.cache.RegisterImpressionRedisAdapter;
import dev.jakubw.adapter.out.persistance.ad.AdPostgresAdapter;
import dev.jakubw.adapter.out.persistance.ad.AdPostgresRepository;
import dev.jakubw.adapter.out.persistance.impression.AdImpressionPostgresAdapter;
import dev.jakubw.adapter.out.persistance.impression.AdImpressionPostgresRepository;
import dev.jakubw.adapter.out.persistance.provider.AdProviderPostgresAdapter;
import dev.jakubw.adapter.out.persistance.provider.AdProviderPostgresRepository;
import dev.jakubw.application.handler.ad.CreateAdHandler;
import dev.jakubw.application.handler.ad.GetAdRecommendationHandler;
import dev.jakubw.application.handler.ad.GetAdsHandler;
import dev.jakubw.application.handler.impression.GetAdImpressionsHandler;
import dev.jakubw.application.handler.impression.RecordImpressionHandler;
import dev.jakubw.application.handler.provider.GetAdProviderHandler;
import dev.jakubw.application.handler.provider.RegisterAdProviderHandler;
import dev.jakubw.domain.port.in.ad.GetRecommendedAdsQry;
import dev.jakubw.domain.port.out.impression.AdImpressionCachePort;
import dev.jakubw.domain.port.out.impression.AdImpressionRepositoryPort;
import dev.jakubw.domain.port.out.provider.AdProviderRepositoryPort;
import dev.jakubw.domain.port.out.ad.AdRepositoryPort;
import dev.jakubw.domain.recommendation.RecommendationEngine;
import dev.jakubw.domain.recommendation.RecommendationStrat;
import dev.jakubw.domain.recommendation.WeightedRecommendationEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@EnableScheduling
@Configuration
public class SpringBootConfig {

    // Scheduler
    @Bean
    public PersistImpressionsSchedulerImpl persistImpressionsScheduler(
            AdImpressionCachePort adImpressionCachePort,
            AdImpressionRepositoryPort adImpressionRepositoryPort
    ){
        return new PersistImpressionsSchedulerImpl(adImpressionCachePort,adImpressionRepositoryPort);
    }


    // Engines
    @Bean
    public RecommendationEngine weightedRecommendationEngine() {
        return new WeightedRecommendationEngine();
    }

    // Engine Strategy
    @Bean
    public Map<RecommendationStrat, RecommendationEngine> recommendationEngines(
            List<RecommendationEngine> engines) {

        return engines.stream()
                .collect(Collectors.toMap(
                        RecommendationEngine::strategy,
                        Function.identity()
                ));
    }

    // Adapters
    @Bean
    GetRecommendationRpcAdapter getRecommendationRpcAdapter(
            GetRecommendedAdsQry query
    ){
        return new GetRecommendationRpcAdapter(query);
    }

    @Bean
    RegisterImpressionRedisAdapter registerImpressionRedisAdapter(
            RedisTemplate<String,Object> redisTemplate
    ){
        return new RegisterImpressionRedisAdapter(redisTemplate);
    }

    @Bean
    AdPostgresAdapter adPostgresAdapter(
            AdPostgresRepository adPostgresRepository,
            AdProviderPostgresRepository adProviderPostgresRepository
    ){
        return new AdPostgresAdapter(adPostgresRepository, adProviderPostgresRepository);
    }
    @Bean
    AdImpressionPostgresAdapter adImpressionPostgresAdapter(
            AdImpressionPostgresRepository adImpressionPostgresRepository,
            AdPostgresRepository adPostgresRepository
    ){
        return new AdImpressionPostgresAdapter(adImpressionPostgresRepository, adPostgresRepository);
    }
    @Bean
    AdProviderPostgresAdapter adProviderPostgresAdapter(
            AdProviderPostgresRepository adProviderPostgresRepository
    ){
        return new AdProviderPostgresAdapter(adProviderPostgresRepository);
    }

    // Handlers
    @Bean
    RecordImpressionHandler recordImpressionHandler(
            AdImpressionCachePort cachePort
    ){
        return new RecordImpressionHandler(cachePort);
    }

    @Bean
    GetAdRecommendationHandler getAdRecommendationHandler(
            AdRepositoryPort adRepositoryPort,
            Map<RecommendationStrat, RecommendationEngine> engines
    ){
        return new GetAdRecommendationHandler(adRepositoryPort,engines);
    }

    @Bean
    RegisterAdProviderHandler registerAdProviderHandler(
            AdProviderRepositoryPort adProviderRepositoryPort
    ){
        return new RegisterAdProviderHandler(adProviderRepositoryPort);
    }

    @Bean
    GetAdProviderHandler getAdProviderHandler(
            AdProviderRepositoryPort adProviderRepositoryPort
    ){
        return new GetAdProviderHandler(adProviderRepositoryPort);
    }

    @Bean
    CreateAdHandler createAdHandler(
        AdRepositoryPort adRepositoryPort
    ){
        return new CreateAdHandler(adRepositoryPort);
    }

    @Bean
    GetAdsHandler getAdsHandler(
            AdRepositoryPort adRepositoryPort
    ){
        return new GetAdsHandler(adRepositoryPort);
    }

    @Bean
    GetAdImpressionsHandler getAdImpressionsHandler(
            AdImpressionRepositoryPort adImpressionRepositoryPort
    ){
        return new GetAdImpressionsHandler(adImpressionRepositoryPort);
    }
}
