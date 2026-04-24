package com.ynov.fantasy_war.infra.bdd;

import com.ynov.fantasy_war.infra.bdd.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<AppUser, Long> {
    /**
     * Recherche un utilisateur par username.
     *
     * @param username le nom d'utilisateur
     * @return un Optional (vide si non trouvé)
     */
    Optional<AppUser> findByUsername(String username);

    /**
     * Vérifie si un utilisateur existe déjà pour ce username.
     *
     * @param username le nom d'utilisateur
     * @return true si un utilisateur existe déjà
     */
    boolean existsByUsername(String username);
}
