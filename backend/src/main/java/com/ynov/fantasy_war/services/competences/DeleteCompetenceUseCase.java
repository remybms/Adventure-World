package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class DeleteCompetenceUseCase {

    private final CompetencesRepository competencesRepository;

    public void execute(UUID id){
        competencesRepository.deleteById(id);
    }
}
