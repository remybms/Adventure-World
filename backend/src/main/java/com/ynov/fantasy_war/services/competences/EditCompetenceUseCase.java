package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.ClasseEntity;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class EditCompetenceUseCase {

    private final CompetencesRepository competencesRepository;

    public void execute(UUID id, Competence competencePayload){
        CompetenceEntity competence = competencesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Competence", id));
        CompetenceMapper.fillCompetence(competencePayload, competence);
        competencesRepository.save(competence);
    }
}
