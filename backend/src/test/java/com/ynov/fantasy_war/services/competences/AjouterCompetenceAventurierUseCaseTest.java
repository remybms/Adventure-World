package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetenceAventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.domain.RequeteInvalideException;
import com.ynov.fantasy_war.domain.competence.CompetenceDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AjouterCompetenceAventurierUseCaseTest {

    private AventurierRepository aventurierRepository;
    private CompetencesRepository competencesRepository;
    private CompetenceAventurierRepository competenceAventurierRepository;
    private CompetenceDomain competenceDomain;
    private AjouterCompetenceAventurierUseCase useCase;

    @BeforeEach
    void setUp(){
        aventurierRepository = mock(AventurierRepository.class);
        competencesRepository = mock(CompetencesRepository.class);
        competenceAventurierRepository = mock(CompetenceAventurierRepository.class);
        competenceDomain = new CompetenceDomain();
        useCase = new AjouterCompetenceAventurierUseCase(aventurierRepository, competencesRepository, competenceAventurierRepository, competenceDomain);
    }

    @Test
    void execute_refuseWhenPrerequisitesNotSatisfied() {
        UUID adventurerId = UUID.randomUUID();
        UUID skillId = UUID.randomUUID();
        AventurierEntity adventurer = new AventurierEntity();
        adventurer.setId(adventurerId);
        adventurer.setNiveau(1);
        when(aventurierRepository.findById(adventurerId)).thenReturn(Optional.of(adventurer));

        CompetenceEntity skill = new CompetenceEntity();
        skill.setId(skillId);
        skill.setNiveauMinimum(10);
        when(competencesRepository.findById(skillId)).thenReturn(Optional.of(skill));

        assertThrows(RequeteInvalideException.class, () -> useCase.execute(adventurerId, skillId));
        verify(competenceAventurierRepository, never()).save(any());
    }
}
