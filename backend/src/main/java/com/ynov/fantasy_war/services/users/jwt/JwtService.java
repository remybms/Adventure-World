package com.ynov.fantasy_war.services.users.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
/**
 * Service de génération de JWT (token renvoyé au client après login).
 *
 * <p>Un JWT contient :</p>
 * <ul>
 *   <li>un header (algo de signature)</li>
 *   <li>des claims (payload : subject, issuer, expiration, etc.)</li>
 *   <li>une signature</li>
 * </ul>
 *
 * <p>Le client (front JS) stocke ensuite le token et l'envoie en :</p>
 * <pre>Authorization: Bearer &lt;token&gt;</pre>
 */
@Service
public class JwtService {

    private final JwtEncoder encoder;
    private final String issuer;
    private final long expMinutes;
    /**
     * @param encoder composant qui signe et encode les JWT
     * @param issuer valeur du claim "iss" (issuer)
     * @param expMinutes durée de validité du token
     */
    public JwtService(
            JwtEncoder encoder,
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.exp-minutes}") long expMinutes
    ) {
        this.encoder = encoder;
        this.issuer = issuer;
        this.expMinutes = expMinutes;
    }
    /**
     * Génère un JWT pour un utilisateur déjà authentifié.
     *
     * <p>On récupère les informations dans {@link Authentication} :</p>
     * <ul>
     *   <li>{@code auth.getName()} = username</li>
     *   <li>{@code auth.getAuthorities()} = roles/permissions</li>
     * </ul>
     *
     * <p>On met les rôles dans un claim "scope" (convention fréquente).</p>
     *
     * @param auth résultat de l'authentification Spring Security
     * @return JWT signé (String)
     */
    public String generate(Authentication auth) {
        Instant now = Instant.now();

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(expMinutes, ChronoUnit.MINUTES))
                .subject(auth.getName())
                .claim("scope", scope) // ex: "ROLE_USER"
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
