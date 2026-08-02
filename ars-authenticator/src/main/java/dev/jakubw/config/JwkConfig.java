package dev.jakubw.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import dev.jakubw.exception.TokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwkConfig {

    @Value("${jwk.rsa.public}")
    private String publicKey;
    @Value("${jwk.rsa.private}")
    private String privateKey;

    @Bean
    public RSAKey rsaKey(){
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");

            byte[] publicRaw = Base64.getDecoder().decode(publicKey);
            byte[] privateRaw = Base64.getDecoder().decode(privateKey);

            PrivateKey priv = factory.generatePrivate(new PKCS8EncodedKeySpec(privateRaw));
            PublicKey pub = factory.generatePublic(new X509EncodedKeySpec(publicRaw));

            return new com.nimbusds.jose.jwk.RSAKey.Builder((RSAPublicKey) pub)
                    .privateKey((RSAPrivateKey) priv)
                    .keyID("ars-kid")
                    .build();
        }catch (Exception e){
            throw new TokenException(e.getMessage(),e.getCause());
        }
    }

    @Bean
    public JWKSet jwkSet(RSAKey rsaKey) {
        return new JWKSet(rsaKey);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
        return new ImmutableJWKSet<>(jwkSet);
    }
}
