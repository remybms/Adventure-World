package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetenceAventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceAventurier;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ListerCompetencesAventurierUseCase {

    private final CompetenceAventurierRepository competenceAventurierRepository;
    private final CompetencesRepository competencesRepository;
    private final AventurierRepository aventurierRepository;
    private final ListerCompetencesUseCase listerCompetencesUseCase;

    public List<Competence> execute(UUID aventurierId) {
        if (!aventurierRepository.existsById(aventurierId)) {
            throw new NotFoundException("Aventurier", aventurierId);
        }

        List<CompetenceAventurier> associations = competenceAventurierRepository.findAllByIdAventurier(aventurierId);
        if (associations.isEmpty()) {
            return List.of();
        }

        Set<UUID> competenceIds = associations.stream()
                .map(CompetenceAventurier::getIdCompetence)
                .collect(Collectors.toSet());

        return competencesRepository.findAllById(competenceIds).stream()
                .map(listerCompetencesUseCase::toDto)
                .toList();
    }
}
