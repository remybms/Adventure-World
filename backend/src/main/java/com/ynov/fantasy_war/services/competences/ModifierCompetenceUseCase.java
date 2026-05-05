package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.domain.competence.CompetenceDomain;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventuriersParCompetenceResponse;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ModifierCompetenceUseCase {

    private final CompetencesRepository competencesRepository;
    private final CompetenceDomain competenceDomain;
    private final ListerAventuriersParCompetenceUseCase listerAventuriersParCompetenceUseCase;

    public void execute(UUID id, Competence competencePayload){
        CompetenceEntity competence = competencesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Competence", id));

        AventuriersParCompetenceResponse aventuriersParCompetence = listerAventuriersParCompetenceUseCase.execute(id);
        List<AventurierDto> aventuriers = aventuriersParCompetence.getPossesseurs();

        competenceDomain.checkModificationPrerequis(competencePayload.getClasseRequise(), competencePayload.getNiveauMinimum(), competencePayload.getMentalMinimum(), competencePayload.getPerceptionMinimum(), competencePayload.getPhysiqueMinimum(), aventuriers);

        CompetenceMapper.fillCompetence(competencePayload, competence);
        competencesRepository.save(competence);
    }
}
