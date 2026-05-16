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

    public void checkCompetencesRequises(List<UUID> competencesRequises, List<UUID> competencesAventurier) {
        if (competencesRequises == null || competencesRequises.isEmpty()) {
            return;
        }
        if (competencesAventurier == null || competencesAventurier.isEmpty()) {
            throw new IllegalKnownCompetenceException();
        }
        for (UUID required : competencesRequises) {
            if (!competencesAventurier.contains(required)) {
                throw new IllegalKnownCompetenceException();
            }
        }
    }
    public void checkCaracteristiquePoints(String caracteristique, int requiredPoints, int aventurierPoints){
        if (requiredPoints > aventurierPoints){
            throw new IllegalCaracteristiquePointsException(caracteristique, requiredPoints);
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
