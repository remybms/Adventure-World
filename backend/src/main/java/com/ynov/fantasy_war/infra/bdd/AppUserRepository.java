package com.ynov.fantasy_war.infra.bdd;

import com.ynov.fantasy_war.infra.bdd.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
