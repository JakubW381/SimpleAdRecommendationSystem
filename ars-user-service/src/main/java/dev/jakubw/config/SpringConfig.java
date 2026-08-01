package dev.jakubw.config;

import dev.jakubw.adapter.out.persistance.AdUserPostgresAdapter;
import dev.jakubw.adapter.out.persistance.AdUserPostgresRepository;
import dev.jakubw.application.handler.GetUserHandler;
import dev.jakubw.domain.port.out.AdUserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // Adapter
    @Bean
    AdUserPostgresAdapter adUserPostgresAdapter(
            AdUserPostgresRepository adUserPostgresRepository
    ){
        return new AdUserPostgresAdapter(adUserPostgresRepository);
    }

    // Handler
    @Bean
    public GetUserHandler getUserHandler(
            AdUserRepositoryPort adUserRepositoryPort
    ){
        return new GetUserHandler(adUserRepositoryPort);
    }
}
