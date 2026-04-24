package com.ynov.fantasy_war.services.users.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Configuration liée au JWT :
 * <ul>
 *   <li>PasswordEncoder (BCrypt) pour enregistrer et vérifier les mots de passe</li>
 *   <li>JwtEncoder / JwtDecoder pour produire et vérifier les tokens</li>
 * </ul>
 *
 * <p>Approche du cours : JWT signé en HS256 (secret partagé).
 * Simple à expliquer et à mettre en place.</p>
 *
 * <p>Note : en production, on préférera souvent RSA/ECDSA (clé privée/clé publique),
 * ou une solution OAuth2 / OpenID Connect.</p>
 */
@Configuration
public class JwtConfig {

    /**
     * Encodeur BCrypt pour les mots de passe.
     *
     * <p>Spring Security utilise cet encodeur pour comparer le mot de passe fourni
     * et le hash stocké en base.</p>
     *
     * @return un PasswordEncoder BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * JwtEncoder : composant qui SIGNE et génère les JWT.
     *
     * <p>On part d'un secret Base64 (démo locale), on le transforme en clé HMAC SHA-256.</p>
     *
     * @param secretB64 secret en Base64 depuis application.yaml
     * @return JwtEncoder Nimbus configuré en HS256
     */
    @Bean
    public JwtEncoder jwtEncoder(@Value("${app.jwt.secret}") String secretB64) {
        byte[] secret = Base64.getDecoder().decode(secretB64);
        var key = new SecretKeySpec(secret, "HmacSHA256");

        // IMPORTANT : forcer HS256 pour éviter les problèmes de sélection de clé
        return NimbusJwtEncoder.withSecretKey(key)
                .algorithm(MacAlgorithm.HS256)
                .build();
    }

    /**
     * JwtDecoder : composant qui VALIDE et décode les JWT (signature, exp, etc.).
     *
     * <p>Le Resource Server (Spring Security) l'utilise automatiquement
     * pour valider les tokens Bearer reçus en header Authorization.</p>
     *
     * @param secretB64 secret en Base64 depuis application.yaml
     * @return JwtDecoder Nimbus configuré en HS256
     */
    @Bean
    public JwtDecoder jwtDecoder(@Value("${app.jwt.secret}") String secretB64) {
        byte[] secret = Base64.getDecoder().decode(secretB64);
        var key = new SecretKeySpec(secret, "HmacSHA256");

        return NimbusJwtDecoder.withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}

