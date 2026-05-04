package com.ynov.fantasy_war.infra.api;

import com.ynov.fantasy_war.infrastructure.web.openapi.api.CompetencesApi;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventuriersParCompetenceResponse;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.ListerAventuriersParCompetence200Response;
import com.ynov.fantasy_war.services.competences.*;
import com.ynov.fantasy_war.services.competences.ObtenirCompetencesDisponiblesUseCase.CompetencesDisponiblesResult;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class CompetenceController implements CompetencesApi {

    private final CreerCompetenceUseCase creerCompetenceUseCase;
    private final ListerCompetencesUseCase listerCompetencesUseCase;
    private final CompetenceParIdUseCase competenceParIdUseCase;
    private final EditCompetenceUseCase editCompetenceUseCase;
    private final DeleteCompetenceUseCase deleteCompetenceUseCase;
    private final ListerAventuriersParCompetenceUseCase listerAventuriersParCompetenceUseCase;
    private final AjouterCompetenceAventurierUseCase ajouterCompetenceAventurierUseCase;

    public CompetenceController(CreerCompetenceUseCase creerCompetenceUseCase, ListerCompetencesUseCase listerCompetencesUseCase, CompetenceParIdUseCase competenceParIdUseCase, EditCompetenceUseCase editCompetenceUseCase, DeleteCompetenceUseCase deleteCompetenceUseCase, ListerAventuriersParCompetenceUseCase listerAventuriersParCompetenceUseCase, AjouterCompetenceAventurierUseCase ajouterCompetenceAventurierUseCase){
        this.creerCompetenceUseCase = creerCompetenceUseCase;
        this.listerCompetencesUseCase = listerCompetencesUseCase;
        this.competenceParIdUseCase = competenceParIdUseCase;
        this.editCompetenceUseCase = editCompetenceUseCase;
        this.deleteCompetenceUseCase = deleteCompetenceUseCase;
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

    }

    @Override
    public void modifierCompetence(UUID id, Competence competence) {
        editCompetenceUseCase.execute(id, competence);
    }

    @Override
    public Competence obtenirCompetence(UUID id) {
        return competenceParIdUseCase.execute(id);
    }

    // @Override
    // public List<Competence> obtenirCompetencesDisponibles(UUID id) {

    // }

      @Override
    public List<Competence> obtenirCompetencesDisponibles(UUID id) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/api/v1/aventuriers/{id}/competences/disponibles")
    public ResponseEntity<CompetencesDisponiblesResult> disponibles(@PathVariable UUID id) {
        return ResponseEntity.ok(obtenirCompetencesDisponiblesUseCase.execute(id));
    }

    @Override
    public void retirerCompetenceAventurier(UUID id, UUID cId) {

    }

    @Override
    public void supprimerCompetence(UUID id) {
        deleteCompetenceUseCase.execute(id);
    }
}
