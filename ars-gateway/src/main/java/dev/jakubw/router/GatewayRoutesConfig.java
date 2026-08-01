package dev.jakubw.router;

import dev.jakubw.filter.JwtClaimsHeaderFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routing(RouteLocatorBuilder builder,
                                JwtClaimsHeaderFilter filter) {
        return builder.routes()
                .route(p -> p
                        .path("/api/ad/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8081")
                ).route(p -> p
                        .path("/api/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8082")
                ).route(p -> p
                        .path("/api/auth/**")
                        .uri("http://localhost:8083")
                ).build();
    }
}
