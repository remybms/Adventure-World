package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.ConflictException;
import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetenceAventurierRepository;
import com.ynov.fantasy_war.infra.bdd.CompetencesRepository;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceAventurier;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RetirerCompetenceAventurierUseCaseTest {

    private CompetenceAventurierRepository competenceAventurierRepository;
    private CompetencesRepository competencesRepository;
    private AventurierRepository aventurierRepository;
    private RetirerCompetenceAventurierUseCase useCase;

    @BeforeEach
    void setUp(){
        competenceAventurierRepository = mock(CompetenceAventurierRepository.class);
        competencesRepository = mock(CompetencesRepository.class);
        aventurierRepository = mock(AventurierRepository.class);
        useCase = new RetirerCompetenceAventurierUseCase(competenceAventurierRepository, competencesRepository, aventurierRepository);
    }

    @Test
    void execute_refuseWhenPrerequisiteForOtherOwnedCompetence() {
        UUID adventurerId = UUID.randomUUID();
        UUID competenceId = UUID.randomUUID();
        when(aventurierRepository.existsById(adventurerId)).thenReturn(true);
        when(competencesRepository.existsById(competenceId)).thenReturn(true);

        CompetenceAventurier association = new CompetenceAventurier();
        association.setIdAventurier(adventurerId);
        association.setIdCompetence(competenceId);
        when(competenceAventurierRepository.findByIdAventurierAndIdCompetence(adventurerId, competenceId)).thenReturn(Optional.of(association));

        // aventurier also has another competence that requires the one being removed
        UUID otherId = UUID.randomUUID();
        CompetenceAventurier association2 = new CompetenceAventurier();
        association2.setIdAventurier(adventurerId);
        association2.setIdCompetence(otherId);
        when(competenceAventurierRepository.findAllByIdAventurier(adventurerId)).thenReturn(List.of(association, association2));

        CompetenceEntity otherCompetence = new CompetenceEntity();
        otherCompetence.setId(otherId);
        otherCompetence.setNom("Combo");
        otherCompetence.setCompetencesRequises(List.of(competenceId));
        when(competencesRepository.findById(otherId)).thenReturn(Optional.of(otherCompetence));

        ConflictException ex = assertThrows(ConflictException.class, () -> useCase.execute(adventurerId, competenceId));
        assertTrue(ex.getMessage().contains("Combo"));
    }

    @Test
    void execute_succeedsWhenNoBlockingPrerequisite() {
        UUID adventurerId = UUID.randomUUID();
        UUID competenceId = UUID.randomUUID();
        when(aventurierRepository.existsById(adventurerId)).thenReturn(true);
        when(competencesRepository.existsById(competenceId)).thenReturn(true);

        CompetenceAventurier association = new CompetenceAventurier();
        association.setIdAventurier(adventurerId);
        association.setIdCompetence(competenceId);
        when(competenceAventurierRepository.findByIdAventurierAndIdCompetence(adventurerId, competenceId)).thenReturn(Optional.of(association));

        when(competenceAventurierRepository.findAllByIdAventurier(adventurerId)).thenReturn(List.of(association));

        useCase.execute(adventurerId, competenceId);

        verify(competenceAventurierRepository).deleteByIdAventurierAndIdCompetence(adventurerId, competenceId);
    }
}
