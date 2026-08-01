package dev.jakubw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.oauth2.server.resource.web.server.authentication.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurity(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/api/user/**").hasRole("USER")
                        .pathMatchers("/api/ad/**").hasRole("PROVIDER")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .bearerTokenConverter(cookieBearerTokenConverter())
                        .jwt(withDefaults())
                );
        return http.build();
    }

    @Bean
    public ServerAuthenticationConverter cookieBearerTokenConverter() {
        return exchange -> {
            HttpCookie cookie = exchange.getRequest()
                    .getCookies()
                    .getFirst("ARSToken");

            if (cookie == null) {
                return Mono.empty();
            }

            return Mono.just(
                    new BearerTokenAuthenticationToken(cookie.getValue())
            );
        };
    }
}