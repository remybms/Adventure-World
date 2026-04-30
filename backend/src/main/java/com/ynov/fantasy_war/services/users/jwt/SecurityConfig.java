package com.ynov.fantasy_war.services.users.jwt;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

import java.util.List;

/**
 * Configuration principale Spring Security.
 *
 * <p>Objectif (API REST + front JS séparé) :</p>
 * <ul>
 *   <li>Pas de session côté serveur (STATELESS)</li>
 *   <li>CSRF désactivé (car pas de cookies de session)</li>
 *   <li>CORS configuré pour autoriser le front</li>
 *   <li>/auth/register et /auth/login publics</li>
 *   <li>Tout le reste protégé par JWT (Bearer token)</li>
 * </ul>
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                // Autorise les requêtes cross-origin selon la config ci-dessous.
                .cors(Customizer.withDefaults())

                // Pour une API stateless sans session cookie, CSRF est inutile.
                .csrf(AbstractHttpConfigurer::disable)

                // H2 Console utilise des frames => autoriser l'affichage dans un iframe (même origine).
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                // API REST => pas de session (chaque requête doit apporter son token).
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Règles d'autorisation des endpoints
                .authorizeHttpRequests(auth -> auth


                        // Endpoints publics
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/**").permitAll()

                        // Préflight CORS (le navigateur envoie OPTIONS avant certaines requêtes)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Console H2 (demo/dev uniquement)
                        .requestMatchers(PathRequest.toH2Console()).permitAll()

                        // Tout le reste requiert un JWT valide
                        .anyRequest().authenticated()
                )
                // Activation du Resource Server JWT :
                // Spring va lire "Authorization: Bearer <token>" et utiliser JwtDecoder.
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter()))
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthConverter() {
        JwtGrantedAuthoritiesConverter gac = new JwtGrantedAuthoritiesConverter();
        gac.setAuthoritiesClaimName("scope");
        gac.setAuthorityPrefix(""); // on garde "ROLE_USER" tel quel

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(gac);
        return converter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        // URLs du front (dev). En prod, ajoute ton domaine réel.
        cfg.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:5173"
        ));

        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Pas de cookies de session => pas besoin de credentials
        cfg.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}

