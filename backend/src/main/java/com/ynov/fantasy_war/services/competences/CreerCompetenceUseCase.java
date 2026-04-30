package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.ClasseEntity;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CreerCompetenceUseCase {

    private final CompetencesRepository competencesRepository;

    public Competence execute(Competence competencePayload){
        log.info("Création d'une compétence");
        CompetenceEntity competence = new CompetenceEntity();
        competence.setId(competencePayload.getId());
        competence.setNom(competencePayload.getNom());
        competence.setDescription(competencePayload.getDescription());
        ClasseEntity classeEntity = ClasseEntity.valueOf(competencePayload.getClasseRequise());
        competence.setClasseRequise(classeEntity);
        competence.setCompetencesRequises(competencePayload.getCompetencesRequises());
        competence.setNiveauMinimum(competencePayload.getNiveauMinimum());
        competencesRepository.save(competence);
        return competencePayload;
    }
}
