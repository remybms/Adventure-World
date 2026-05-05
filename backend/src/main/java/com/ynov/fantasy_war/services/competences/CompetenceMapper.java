package com.ynov.fantasy_war.services.competences;

import com.ynov.fantasy_war.domain.aventurier.AventurierDomain;
import com.ynov.fantasy_war.domain.competence.CompetenceDomain;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.ClasseEntity;
import com.ynov.fantasy_war.infra.bdd.entity.CompetenceEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;

public class CompetenceMapper {
    public static Competence toDto(CompetenceEntity competence){
        Competence competenceResult = new Competence();
        competenceResult.setId(competence.getId());
        competenceResult.setNom(competence.getNom());
        competenceResult.setDescription(competence.getDescription());
        competenceResult.setCompetencesRequises(competence.getCompetencesRequises());
        competenceResult.setClasseRequise(competence.getClasseRequise().toString());
        competenceResult.setNiveauMinimum(competence.getNiveauMinimum());
        competenceResult.setMentalMinimum(competence.getMentalRequis());
        competenceResult.setPerceptionMinimum(competence.getPerceptionRequise());
        competenceResult.setPhysiqueMinimum(competence.getPhysiqueRequis());
        return competenceResult;
    }

    public static CompetenceEntity fillCompetence(Competence competencePayload){
        CompetenceEntity competence = new CompetenceEntity();
        competence.setId(competencePayload.getId());
        competence.setNom(competencePayload.getNom());
        competence.setDescription(competencePayload.getDescription());
        ClasseEntity classeEntity = ClasseEntity.valueOf(competencePayload.getClasseRequise());
        competence.setClasseRequise(classeEntity);
        competence.setCompetencesRequises(competencePayload.getCompetencesRequises());
        competence.setNiveauMinimum(competencePayload.getNiveauMinimum());
        competence.setPerceptionRequise(competencePayload.getPerceptionMinimum());
        competence.setMentalRequis(competencePayload.getMentalMinimum());
        competence.setPhysiqueRequis(competencePayload.getPhysiqueMinimum());
        return competence;
    }

    public static boolean checkCompetence(CompetenceDomain competenceDomain, CompetenceEntity competence, AventurierEntity aventurier){
        try{
            competenceDomain.checkNiveauRequis(competence.getNiveauMinimum(), aventurier.getNiveau());
            competenceDomain.checkCaracteristiquePoints("Mental", competence.getMentalRequis(), aventurier.getMental());
            competenceDomain.checkCaracteristiquePoints("Perception", competence.getPerceptionRequise(), aventurier.getPerception());
            competenceDomain.checkCaracteristiquePoints("Physique", competence.getPhysiqueRequis(), aventurier.getPhysique());
            competenceDomain.checkClasseRequise(competence.getClasseRequise().toString(), aventurier.getClasse().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
