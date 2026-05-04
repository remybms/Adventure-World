package com.ynov.fantasy_war.infra.bdd;

import com.ynov.fantasy_war.infra.bdd.entity.CompetenceAventurier;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CompetenceAventurierRepository extends JpaRepository<CompetenceAventurier, UUID> {

    @Query("SELECT ca.idCompetence FROM CompetenceAventurier ca WHERE ca.idAventurier = :aventurierId")
    List<UUID> findCompetenceIdsByAventurierId(@Param("aventurierId") UUID aventurierId);
}
