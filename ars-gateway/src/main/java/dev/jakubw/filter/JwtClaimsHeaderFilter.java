package dev.jakubw.filter;

import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class JwtClaimsHeaderFilter implements GatewayFilter {

    @Override
    @NullMarked
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        return exchange.getPrincipal()
                .cast(JwtAuthenticationToken.class)
                .flatMap(jwtAuth -> {

                    Jwt jwt = jwtAuth.getToken();
                    String id = jwt.getSubject();

                    ServerHttpRequest.Builder requestBuilder =
                            exchange.getRequest()
                                    .mutate();

                    requestBuilder.headers(headers -> {
                        headers.remove("X-User-Id");
                        headers.remove("X-Provider-Id");
                    });

                    Collection<String> authorities =
                            jwtAuth.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .toList();

                    if (authorities.contains("ROLE_USER")) {
                        requestBuilder.header("X-User-Id", id);
                    }

                    if (authorities.contains("ROLE_PROVIDER")) {
                        requestBuilder.header("X-Provider-Id", id);
                    }

                    return chain.filter(
                            exchange.mutate()
                                    .request(requestBuilder.build())
                                    .build()
                    );
                })
                .switchIfEmpty(chain.filter(exchange));
    }
}
