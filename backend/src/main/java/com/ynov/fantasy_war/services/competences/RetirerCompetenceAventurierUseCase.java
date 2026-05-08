package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.ConflictException;
import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.domain.competence.CompetenceDomain;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetenceAventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceAventurier;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import jakarta.transaction.Transactional;
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
public class RetirerCompetenceAventurierUseCase {

    private final CompetenceAventurierRepository competenceAventurierRepository;
    private final CompetencesRepository competencesRepository;
    private final AventurierRepository aventurierRepository;
    private final CompetenceDomain competenceDomain;

    @Transactional
    public void execute(UUID aventurierId, UUID competenceId) {
        // check if the eventurer exists
        if (!aventurierRepository.existsById(aventurierId)) {
            throw new NotFoundException("Aventurier", aventurierId);
        }

        // same but for the competence
        if (!competencesRepository.existsById(competenceId)) {
            throw new NotFoundException("Compétence", competenceId);
        }

        // check if the adventurer has this skill
        CompetenceAventurier association = competenceAventurierRepository
                .findByIdAventurierAndIdCompetence(aventurierId, competenceId)
                .orElseThrow(() -> new NotFoundException("L'aventurier ne possède pas cette compétence"));

        // retrieve all skills of the adventurer
        List<CompetenceAventurier> associations = competenceAventurierRepository.findAllByIdAventurier(aventurierId);
        Set<UUID> competenceIdsPossedees = associations.stream()
                .map(CompetenceAventurier::getIdCompetence)
                .collect(Collectors.toSet());

        // check that this skill is not a prerequisite for another skill the adventurer has
        List<Competence> competencesPossedees = competencesRepository.findAllById(competenceIdsPossedees)
                .stream()
                .map(this::toDto)
                .toList();

        competenceDomain.checkRetraitCompetence(competenceId, competencesPossedees);

        // delete the association between the adventurer and the skill 
        competenceAventurierRepository.deleteByIdAventurierAndIdCompetence(aventurierId, competenceId);
        log.info("Compétence {} retirée de l'aventurier {}", competenceId, aventurierId);
    }

    public Competence toDto(CompetenceEntity competence){
        return CompetenceMapper.toDto(competence);
    }
}