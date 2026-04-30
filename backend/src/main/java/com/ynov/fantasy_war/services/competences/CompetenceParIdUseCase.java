package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class CompetenceParIdUseCase {

    private final CompetencesRepository competencesRepository;

    public Competence execute(UUID id){
        CompetenceEntity competence = competencesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compétence", id));
        Competence competenceResult = new Competence();
        competenceResult.setId(competence.getId());
        competenceResult.setNom(competence.getNom());
        competenceResult.setDescription(competence.getDescription());
        competenceResult.setCompetencesRequises(competence.getCompetencesRequises());
        competenceResult.setClasseRequise(competence.getClasseRequise().toString());
        competenceResult.setNiveauMinimum(competence.getNiveauMinimum());
        return competenceResult;
    }
}
