package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.ConflictException;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetenceAventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceAventurier;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import com.ynov.fantasy_war.domain.competence.CompetenceDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModifierCompetenceUseCaseTest {

    private CompetencesRepository competencesRepository;
    private CompetenceAventurierRepository competenceAventurierRepository;
    private AventurierRepository aventurierRepository;
    private CompetenceDomain competenceDomain;
    private ModifierCompetenceUseCase useCase;

    @BeforeEach
    void setUp(){
        competencesRepository = mock(CompetencesRepository.class);
        competenceAventurierRepository = mock(CompetenceAventurierRepository.class);
        aventurierRepository = mock(AventurierRepository.class);
        competenceDomain = new CompetenceDomain();
        useCase = new ModifierCompetenceUseCase(competencesRepository, competenceAventurierRepository, aventurierRepository, competenceDomain);
    }

    @Test
    void execute_refuseIfOwnersWouldBecomeInvalid() {
        UUID id = UUID.randomUUID();
        CompetenceEntity existing = new CompetenceEntity();
        existing.setId(id);
        when(competencesRepository.findById(id)).thenReturn(Optional.of(existing));

        UUID ownerId = UUID.randomUUID();
        CompetenceAventurier association = new CompetenceAventurier();
        association.setIdAventurier(ownerId);
        when(competenceAventurierRepository.findByIdCompetence(id)).thenReturn(List.of(association));

        AventurierEntity adventurer = new AventurierEntity();
        adventurer.setId(ownerId);
        adventurer.setNom("Jean");
        adventurer.setNiveau(1);
        when(aventurierRepository.findAllById(List.of(ownerId))).thenReturn(List.of(adventurer));

        Competence payload = new Competence();
        payload.setNiveauMinimum(10); // now requires level 10 -> owner with level 1 invalid
        payload.setPerceptionMinimum(0);
        payload.setMentalMinimum(0);
        payload.setPhysiqueMinimum(0);

        ConflictException ex = assertThrows(ConflictException.class, () -> useCase.execute(id, payload));
        assertTrue(ex.getMessage().contains("Jean"));
        verify(competencesRepository, never()).save(any());
    }

    @Test
    void execute_succeedsWhenNoInvalidOwners() {
        UUID id = UUID.randomUUID();
        CompetenceEntity existing = new CompetenceEntity();
        existing.setId(id);
        when(competencesRepository.findById(id)).thenReturn(Optional.of(existing));

        when(competenceAventurierRepository.findByIdCompetence(id)).thenReturn(List.of());

        Competence payload = new Competence();
        payload.setNiveauMinimum(1);
        payload.setPerceptionMinimum(0);
        payload.setMentalMinimum(0);
        payload.setPhysiqueMinimum(0);

        useCase.execute(id, payload);

        verify(competencesRepository).save(any());
    }
}
