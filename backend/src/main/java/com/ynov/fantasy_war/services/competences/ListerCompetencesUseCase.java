package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.ClasseEntity;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ListerCompetencesUseCase {

    private final CompetencesRepository competencesRepository;

    public List<Competence> execute(){
        List<CompetenceEntity> competences = competencesRepository.findAll();
        return competences.stream()
                .map(this::toDto)
                .toList();
    }

    public Competence toDto(CompetenceEntity competence){
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
