package com.ynov.fantasy_war.infra.api;

import com.ynov.fantasy_war.infrastructure.web.openapi.api.CompetencesApi;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import com.ynov.fantasy_war.services.competences.CreerCompetenceUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CompetenceController implements CompetencesApi {

    private final CreerCompetenceUseCase creerCompetenceUseCase;

    public CompetenceController(CreerCompetenceUseCase creerCompetenceUseCase){
        this.creerCompetenceUseCase = creerCompetenceUseCase;
    }

    @Override
    public void ajouterCompetenceAventurier(UUID aventurier, UUID competence){

    }

    @Override
    public Competence creerCompetence(Competence competence){
        return creerCompetenceUseCase.execute(competence);
    }

    @Override
    public void listerAventuriersParCompetence(UUID competence){

    }

    @Override
    public List<Competence> listerCompetences(){

    }

    @Override
    public List<Competence> listerCompetencesAventurier(UUID aventurier){

    }

    @Override
    public void modifierCompetence(UUID id) {

    }

    @Override
    public Competence obtenirCompetence(UUID id) {

    }

    @Override
    public List<Competence> obtenirCompetencesDisponibles(UUID id) {

    }

    @Override
    public void retirerCompetenceAventurier(UUID id, UUID cId) {

    }

    @Override
    public void supprimerCompetence(UUID id) {

    }
}
