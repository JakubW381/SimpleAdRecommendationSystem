package dev.jakubw.conifg;

import dev.jakubw.adapter.out.persistance.ad.AdPostgresAdapter;
import dev.jakubw.adapter.out.persistance.ad.AdPostgresRepository;
import dev.jakubw.adapter.out.persistance.impression.AdImpressionPostgresAdapter;
import dev.jakubw.adapter.out.persistance.impression.AdImpressionPostgresRepository;
import dev.jakubw.adapter.out.persistance.provider.AdProviderPostgresAdapter;
import dev.jakubw.adapter.out.persistance.provider.AdProviderPostgresRepository;
import dev.jakubw.application.handler.ad.CreateAdHandler;
import dev.jakubw.application.handler.ad.GetAdsHandler;
import dev.jakubw.application.handler.impression.GetAdImpressionsHandler;
import dev.jakubw.application.handler.provider.GetAdProviderHandler;
import dev.jakubw.application.handler.provider.RegisterAdProviderHandler;
import dev.jakubw.domain.port.out.impression.AdImpressionRepositoryPort;
import dev.jakubw.domain.port.out.provider.AdProviderRepositoryPort;
import dev.jakubw.domain.port.out.ad.AdRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootConfig {

    // Adapters
    @Bean
    AdPostgresAdapter adPostgresAdapter(
            AdPostgresRepository adPostgresRepository,
            AdProviderPostgresRepository adProviderPostgresRepository
    ){
        return new AdPostgresAdapter(adPostgresRepository, adProviderPostgresRepository);
    }
    @Bean
    AdImpressionPostgresAdapter adImpressionPostgresAdapter(
            AdImpressionPostgresRepository adImpressionPostgresRepository
    ){
        return new AdImpressionPostgresAdapter(adImpressionPostgresRepository);
    }
    @Bean
    AdProviderPostgresAdapter adProviderPostgresAdapter(
            AdProviderPostgresRepository adProviderPostgresRepository
    ){
        return new AdProviderPostgresAdapter(adProviderPostgresRepository);
    }

    // Handlers

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
