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

                // DOCS
                .route("ad-docs", r -> r
                        .path("/api/docs/ad/**")
                        .filters(f -> f.rewritePath(
                                "/api/docs/ad/(?<segment>.*)",
                                "/${segment}"
                        ))
                        .uri("http://localhost:8081"))

                .route("user-docs", r -> r
                        .path("/api/docs/user/**")
                        .filters(f -> f.rewritePath(
                                "/api/docs/user/(?<segment>.*)",
                                "/${segment}"
                        ))
                        .uri("http://localhost:8082"))

                .route("auth-docs", r -> r
                        .path("/api/docs/auth/**")
                        .filters(f -> f.rewritePath(
                                "/api/docs/auth/(?<segment>.*)",
                                "/${segment}"
                        ))
                        .uri("http://localhost:8083"))

                // API
                .route("ad-service", r -> r
                        .path("/api/ad/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8081"))

                .route("user-service", r -> r
                        .path("/api/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8082"))

                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .uri("http://localhost:8083"))

                .build();
    }
}
