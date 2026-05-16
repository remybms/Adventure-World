package com.ynov.fantasy_war.domain.competence;

import com.ynov.fantasy_war.domain.ConflictException;

import java.util.UUID;

public class IllegalCompetenceDependencyException extends ConflictException {
    public IllegalCompetenceDependencyException(UUID idCompetence) {
        super("L'aventurier a besoin de cette compétence comme prérequis pour la compétence d'id : " + idCompetence);
    }
}
