package dev.jakubw.router;

import dev.jakubw.filter.JwtClaimsHeaderFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Value("${services.ad-service.uri}")
    private String adServiceUri;

    @Value("${services.user-service.uri}")
    private String userServiceUri;

    @Value("${services.auth-service.uri}")
    private String authServiceUri;

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
                        .uri(adServiceUri))

                .route("user-docs", r -> r
                        .path("/api/docs/user/**")
                        .filters(f -> f.rewritePath(
                                "/api/docs/user/(?<segment>.*)",
                                "/${segment}"
                        ))
                        .uri(userServiceUri))

                .route("auth-docs", r -> r
                        .path("/api/docs/auth/**")
                        .filters(f -> f.rewritePath(
                                "/api/docs/auth/(?<segment>.*)",
                                "/${segment}"
                        ))
                        .uri(authServiceUri))

                // API
                .route("ad-service", r -> r
                        .path("/api/ad/**")
                        .filters(f -> f.filter(filter))
                        .uri(adServiceUri))

                .route("user-service", r -> r
                        .path("/api/user/**")
                        .filters(f -> f.filter(filter))
                        .uri(userServiceUri))

                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .uri(authServiceUri))

                .build();
    }
}