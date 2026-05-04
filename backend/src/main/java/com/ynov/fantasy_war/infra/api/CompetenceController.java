package com.ynov.fantasy_war.infra.api;

import com.ynov.fantasy_war.infrastructure.web.openapi.api.CompetencesApi;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventuriersParCompetenceResponse;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.ListerAventuriersParCompetence200Response;
import com.ynov.fantasy_war.services.competences.*;
import com.ynov.fantasy_war.services.competences.ListerCompetencesAventurierUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class CompetenceController implements CompetencesApi {

    private final CreerCompetenceUseCase creerCompetenceUseCase;
    private final ListerCompetencesUseCase listerCompetencesUseCase;
    private final ListerCompetencesAventurierUseCase listerCompetencesAventurierUseCase;
    private final CompetenceParIdUseCase competenceParIdUseCase;
    private final EditCompetenceUseCase editCompetenceUseCase;
    private final DeleteCompetenceUseCase deleteCompetenceUseCase;
    private final RetirerCompetenceAventurierUseCase retirerCompetenceAventurierUseCase;
    private final ListerAventuriersParCompetenceUseCase listerAventuriersParCompetenceUseCase;
    private final AjouterCompetenceAventurierUseCase ajouterCompetenceAventurierUseCase;

    public CompetenceController(CreerCompetenceUseCase creerCompetenceUseCase, ListerCompetencesUseCase listerCompetencesUseCase, ListerCompetencesAventurierUseCase listerCompetencesAventurierUseCase, CompetenceParIdUseCase competenceParIdUseCase, EditCompetenceUseCase editCompetenceUseCase, DeleteCompetenceUseCase deleteCompetenceUseCase, ListerAventuriersParCompetenceUseCase listerAventuriersParCompetenceUseCase, AjouterCompetenceAventurierUseCase ajouterCompetenceAventurierUseCase, RetirerCompetenceAventurierUseCase retirerCompetenceAventurierUseCase){
        this.creerCompetenceUseCase = creerCompetenceUseCase;
        this.listerCompetencesUseCase = listerCompetencesUseCase;
        this.listerCompetencesAventurierUseCase = listerCompetencesAventurierUseCase;
        this.competenceParIdUseCase = competenceParIdUseCase;
        this.editCompetenceUseCase = editCompetenceUseCase;
        this.deleteCompetenceUseCase = deleteCompetenceUseCase;
        this.retirerCompetenceAventurierUseCase = retirerCompetenceAventurierUseCase;
        this.listerAventuriersParCompetenceUseCase = listerAventuriersParCompetenceUseCase;
        this.ajouterCompetenceAventurierUseCase = ajouterCompetenceAventurierUseCase;
    }

    @Override
    public void ajouterCompetenceAventurier(UUID aventurier, UUID competence){
        ajouterCompetenceAventurierUseCase.execute(aventurier, competence);
    }

    @Override
    public Competence creerCompetence(Competence competence){
        return creerCompetenceUseCase.execute(competence);
    }

    @Override
    public AventuriersParCompetenceResponse listerAventuriersParCompetence(UUID competence){
        return listerAventuriersParCompetenceUseCase.execute(competence);
    }

    @Override
    public List<Competence> listerCompetences(){
        return listerCompetencesUseCase.execute();
    }

    @Override
    public List<Competence> listerCompetencesAventurier(UUID aventurier){
        return listerCompetencesAventurierUseCase.execute(aventurier);
    }

    @Override
    public void modifierCompetence(UUID id, Competence competence) {
        editCompetenceUseCase.execute(id, competence);
    }

    @Override
    public Competence obtenirCompetence(UUID id) {
        return competenceParIdUseCase.execute(id);
    }

    @Override
    public List<Competence> obtenirCompetencesDisponibles(UUID id) {
        return List.of();
    }

    @Override
    public void retirerCompetenceAventurier(UUID id, UUID cId) {
        retirerCompetenceAventurierUseCase.execute(id, cId);
    }

    @Override
    public void supprimerCompetence(UUID id) {
        deleteCompetenceUseCase.execute(id);
    }
}
