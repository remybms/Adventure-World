package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.domain.competence.CompetenceDomain;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetenceAventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceAventurier;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class AjouterCompetenceAventurierUseCase {

    private final AventurierRepository aventurierRepository;
    private final CompetencesRepository competencesRepository;
    private final CompetenceAventurierRepository competenceAventurierRepository;
    private final CompetenceDomain competenceDomain;

    public void execute(UUID idAventurier, UUID idCompetence){
        AventurierEntity aventurier = aventurierRepository.findById(idAventurier)
                .orElseThrow(() -> new NotFoundException("Aventurier", idAventurier));

        CompetenceEntity competence = competencesRepository.findById(idCompetence)
                .orElseThrow(() -> new NotFoundException("Compétence", idCompetence));

        CompetenceMapper.checkCompetence(competenceDomain, competence, aventurier);

        CompetenceAventurier competenceAventurier = new CompetenceAventurier();
        competenceAventurier.setIdCompetence(competence.getId());
        competenceAventurier.setIdAventurier(aventurier.getId());
        competenceAventurierRepository.save(competenceAventurier);
    }

}
