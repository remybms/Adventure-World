package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
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
        return CompetenceMapper.toDto(competence);
    }

}
