package dev.jakubw.controller;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/.well-known")
@RequiredArgsConstructor
public class JwkController {

    private final JWKSet jwkSet;

    @GetMapping("/jwks.json")
    public Map<String, Object> getJwkSet() {
        return jwkSet.toPublicJWKSet().toJSONObject();
    }
}
