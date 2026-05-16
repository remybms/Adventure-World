package com.ynov.fantasy_war.domain.competence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
    }
    public void checkCaracteristiquePoints(String caracteristique, int requiredPoints, int aventurierPoints){
        if (requiredPoints > aventurierPoints){
            throw new IllegalCaracteristiquePointsException(caracteristique, requiredPoints);
        }
    }
}
