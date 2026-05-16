package com.ynov.fantasy_war.domain.competence;

import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.Competence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class CompetenceDomain {
    public void checkClasseRequise(String classeRequise, String classeAventurier){
        if(!classeRequise.equals(classeAventurier)){
            throw new IllegalClasseException(classeRequise);
        }
    }

    public void checkNiveauRequis(int niveauRequis, int niveauAventurier){
        if (niveauRequis > niveauAventurier){
            throw new IllegalLevelException(niveauRequis);
        }
    }

    public void checkCompetencesRequises(List<UUID> competencesRequises, List<UUID> competencesAventurier){
        if (competencesRequises == null || competencesRequises.isEmpty()){
            return;
        }
        if (competencesAventurier == null || competencesAventurier.isEmpty()){
            throw new IllegalKnownCompetenceException();
        }
        for (UUID required : competencesRequises) {
            if (!competencesAventurier.contains(required)){
                throw new IllegalKnownCompetenceException();
            }
    }
    public void checkCaracteristiquePoints(String caracteristique, int requiredPoints, int aventurierPoints){
        if (requiredPoints > aventurierPoints){
            throw new IllegalCaracteristiquePointsException(caracteristique, requiredPoints);
        }
    }

    public void checkModificationPrerequis(
            String nouvelleClasseRequise,
            Integer nouveauNiveauMinimum,
            Integer nouveauMental,
            Integer nouvellePerception,
            Integer nouveauPhysique,
            List<AventurierDto> aventuriersPossedantCompetence
    ) {
        List<UUID> aventuriersInvalides = new ArrayList<>();

        for (AventurierDto aventurier : aventuriersPossedantCompetence) {

            try {
                if (nouvelleClasseRequise != null) {
                    checkClasseRequise(nouvelleClasseRequise, aventurier.getClasse().toString());
                }

                if (nouveauNiveauMinimum != null) {
                    checkNiveauRequis(nouveauNiveauMinimum, aventurier.getNiveau());
                }

                if (nouveauMental != null) {
                    checkCaracteristiquePoints(
                            "mental",
                            nouveauMental,
                            aventurier.getMental()
                    );
                }
                if (nouvellePerception != null) {
                    checkCaracteristiquePoints(
                            "mental",
                            nouvellePerception,
                            aventurier.getPerception()
                    );
                }
                if (nouveauPhysique != null) {
                    checkCaracteristiquePoints(
                            "mental",
                            nouveauPhysique,
                            aventurier.getPhysique()
                    );
                }

            } catch (RuntimeException e) {
                aventuriersInvalides.add(aventurier.getId());
            }
        }

        if (!aventuriersInvalides.isEmpty()) {
            throw new IllegalCompetenceModificationException(aventuriersInvalides);
        }
    }

    public void checkRetraitCompetence(
            UUID competenceASupprimer,
            List<Competence> competencesPossedees
    ) {

        for (Competence competence : competencesPossedees) {
            List<UUID> prerequis = competence.getCompetencesRequises();

            if (prerequis != null && prerequis.contains(competenceASupprimer)) {
                throw new IllegalCompetenceDependencyException(competence.getId());
            }
        }
    }
}
