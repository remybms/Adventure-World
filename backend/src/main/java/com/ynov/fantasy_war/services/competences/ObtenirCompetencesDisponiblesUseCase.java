package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetenceAventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import com.ynov.fantasy_war.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class ObtenirCompetencesDisponiblesUseCase {

    public record CompetenceBloquee(
            Competence competence,
            List<Competence> prerequisManquants
    ) {}

    public record CompetencesDisponiblesResult(
            List<Competence> acquerables,
            List<CompetenceBloquee> bloquees
    ) {}

    private final AventurierRepository aventurierRepository;
    private final CompetencesRepository competencesRepository;
    private final CompetenceAventurierRepository competenceAventurierRepository;
    private final ListerCompetencesUseCase listerCompetencesUseCase;

    public CompetencesDisponiblesResult execute(UUID aventurierId) {

        aventurierRepository.findById(aventurierId)
                .orElseThrow(() -> new NotFoundException("Aventurier introuvable : " + aventurierId));

        Set<UUID> idsAcquis = new HashSet<>(
                competenceAventurierRepository.findCompetenceIdsByAventurierId(aventurierId)
        );

        List<CompetenceEntity> toutes = competencesRepository.findAll();
        Map<UUID, CompetenceEntity> parId = new HashMap<>();
        for (CompetenceEntity c : toutes) parId.put(c.getId(), c);

        List<Competence> acquerables = new ArrayList<>();
        List<CompetenceBloquee> bloquees = new ArrayList<>();

        for (CompetenceEntity competence : toutes) {
            if (idsAcquis.contains(competence.getId())) continue;

            List<Competence> prerequisManquants = new ArrayList<>();

            if (competence.getCompetencesRequises() != null) {
                for (UUID reqId : competence.getCompetencesRequises()) {
                    if (!idsAcquis.contains(reqId)) {
                        CompetenceEntity manquante = parId.get(reqId);
                        if (manquante != null) {
                            prerequisManquants.add(listerCompetencesUseCase.toDto(manquante));
                        }
                    }
                }
            }

            if (prerequisManquants.isEmpty()) {
                acquerables.add(listerCompetencesUseCase.toDto(competence));
            } else {
                bloquees.add(new CompetenceBloquee(
                        listerCompetencesUseCase.toDto(competence),
                        prerequisManquants
                ));
            }
        }

        return new CompetencesDisponiblesResult(acquerables, bloquees);
    }
}