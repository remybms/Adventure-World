package com.ynov.fantasy_war.domain.competence;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CompetenceDomain {
    public void checkClasseRequise(String classeRequise, String classeAventurier){
        if(classeRequise != classeAventurier){
            throw new IllegalClasseException(classeRequise);
        }
    }

    public void checkNiveauRequis(int niveauRequis, int niveauAventurier){
        if (niveauRequis > niveauAventurier){
            throw new IllegalLevelException(niveauRequis);
        }
    }

    public void checkCompetencesRequises(List<UUID> competencesRequises, List<UUID> competencesAventurier){
        for (int i = 0; i < competencesAventurier.size(); i++) {
            for (int j = 0; j < competencesRequises.size(); j++) {
                if (competencesAventurier.get(i) != competencesRequises.get(j)){
                    throw new IllegalKnownCompetenceException();
                }
            }
        }
    }
    public void checkCaracteristiquePoints(String caracteristique, int requiredPoints, int aventurierPoints){
        if (requiredPoints > aventurierPoints){
            throw new IllegalCaracteristiquePointsException(caracteristique, requiredPoints);
        }
    }
}
