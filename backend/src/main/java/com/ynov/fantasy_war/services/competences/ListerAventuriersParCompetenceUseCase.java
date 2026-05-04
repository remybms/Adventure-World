package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.domain.competence.CompetenceDomain;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetenceAventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.ClasseEntity;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceAventurier;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventuriersParCompetenceResponse;
import com.ynov.fantasy_war.services.aventurier.AventurierMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ListerAventuriersParCompetenceUseCase {

    private final CompetenceAventurierRepository competenceAventurierRepository;
    private final AventurierRepository aventurierRepository;
    private final CompetenceDomain competenceDomain;
    private final CompetencesRepository competencesRepository;

    public AventuriersParCompetenceResponse execute(UUID idCompetence){

        CompetenceEntity competence = competencesRepository.findById(idCompetence)
                .orElseThrow(() -> new NotFoundException("Compétence", idCompetence));

        List<UUID> IdPossesseurs = competenceAventurierRepository
                .findByIdCompetence(idCompetence)
                .stream()
                .map(CompetenceAventurier::getIdAventurier)
                .toList();

        List<AventurierDto> possesseurs = aventurierRepository.findAllById(IdPossesseurs)
                .stream()
                .map(this::toDto)
                .toList();

        List<AventurierDto> eligibles = aventurierRepository.findAll()
                .stream()
                .filter(a -> CompetenceMapper.checkCompetence(competenceDomain, competence, a))
                .filter(a -> !possesseurs.contains(a))
                .map(this::toDto)
                .toList();

        AventuriersParCompetenceResponse response = new AventuriersParCompetenceResponse();
        response.setPossesseurs(possesseurs);
        response.setEligibles(eligibles);

        return response;
    }

    private AventurierDto toDto(AventurierEntity aventurier) {
        return AventurierMapper.toDto(aventurier);
    }
}
