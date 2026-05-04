package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.domain.competence.CompetenceDomain;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetenceAventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceAventurier;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.CompetencesDisponiblesResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class ObtenirCompetencesDisponiblesUseCase {

    /*public record CompetenceBloquee(
            Competence competence,
            List<Competence> prerequisManquants
    ) {}*/

    private final AventurierRepository aventurierRepository;
    private final CompetencesRepository competencesRepository;
    private final CompetenceAventurierRepository competenceAventurierRepository;
    private final CompetenceDomain competenceDomain;

    public CompetencesDisponiblesResult execute(UUID aventurierId) {
        AventurierEntity aventurier = aventurierRepository.findById(aventurierId)
                .orElseThrow(() -> new NotFoundException("Aventurier",  aventurierId));

        List<UUID> IdsCompetencesAcquises = competenceAventurierRepository.findByIdAventurier(aventurierId)
                .stream()
                .map(CompetenceAventurier::getIdCompetence)
                .toList();

        List<Competence> competencesAcquises = competencesRepository.findAllById(IdsCompetencesAcquises)
                .stream()
                .map(this::toDto)
                .toList();


        List<Competence> disponibles = competencesRepository.findAll()
                .stream()
                .filter(c -> CompetenceMapper.checkCompetence(competenceDomain, c, aventurier))
                .map(this::toDto)
                .filter(c -> !competencesAcquises.contains(c))
                .toList();

        List<Competence> bloquees = competencesRepository.findAll()
                .stream()
                .filter(c -> !CompetenceMapper.checkCompetence(competenceDomain, c, aventurier))
                .map(this::toDto)
                .filter(c -> !competencesAcquises.contains(c))
                .toList();

        CompetencesDisponiblesResult result = new CompetencesDisponiblesResult();
        result.setDisponibles(disponibles);
        result.setBloquees(bloquees);

        return result;
    }

    private Competence toDto (CompetenceEntity competenceEntity) {
        return CompetenceMapper.toDto(competenceEntity);
    }
}