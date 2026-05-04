package com.ynov.fantasy_war.infra.bdd;

import com.ynov.fantasy_war.infra.bdd.entity.CompetenceAventurier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompetenceAventurierRepository extends JpaRepository<CompetenceAventurier, UUID> {
    List<CompetenceAventurier> findAllByIdAventurier(UUID idAventurier);
}
