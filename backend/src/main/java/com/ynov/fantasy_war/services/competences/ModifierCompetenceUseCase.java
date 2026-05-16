package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.domain.competence.CompetenceDomain;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.CompetenceAventurierRepository;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.domain.ConflictException;
import com.ynov.fantasy_war.domain.competence.CompetenceDomain;
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
    private final CompetenceAventurierRepository competenceAventurierRepository;
    private final AventurierRepository aventurierRepository;
    private final CompetenceDomain competenceDomain;

    public void execute(UUID id, Competence competencePayload){
        CompetenceEntity competence = competencesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Competence", id));

        // simulate the updated competence in memory
        CompetenceEntity updated = CompetenceMapper.fillCompetence(competencePayload, new CompetenceEntity());

        // find all aventuriers who possess this competence
        var associations = competenceAventurierRepository.findByIdCompetence(id);
        var aventurierIds = associations.stream().map(a -> a.getIdAventurier()).toList();
        var aventuriers = aventurierRepository.findAllById(aventurierIds);

        // check each aventurier against the new prerequisites
        var invalids = aventuriers.stream()
                .filter(a -> !CompetenceMapper.checkCompetence(competenceDomain, updated, a))
                .map(a -> a.getNom())
                .toList();

        if (!invalids.isEmpty()){
            throw new ConflictException("Modification refusée : aventuriers impactés -> " + String.join(", ", invalids));
        }

        // persist changes
        CompetenceMapper.fillCompetence(competencePayload, competence);
        competencesRepository.save(competence);
    }
}
