package dev.jakubw.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.jakubw.exception.TokenException;
import dev.jakubw.model.AuthDetailsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.InstantSource;
import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final RSAKey rsaKey;

    public String generateToken(AuthDetailsEntity details){
        Instant timestamp = Instant.now();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("ars-token")
                .subject(details.getId())
                .claim("email",details.getEmail())
                .claim("roles",details.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .issueTime(Date.from(timestamp))
                .expirationTime(Date.from(timestamp.plus(1, ChronoUnit.DAYS)))
                .build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(rsaKey.getKeyID())
                .build();
        SignedJWT signedJWT = new SignedJWT(header, claims);
        try{
            signedJWT.sign(new RSASSASigner(rsaKey));
        }catch (JOSEException e) {
            throw new TokenException(e.getMessage(),e.getCause());
        }

        return signedJWT.serialize();
    }
}