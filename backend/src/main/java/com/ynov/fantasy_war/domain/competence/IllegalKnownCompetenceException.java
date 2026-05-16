package com.ynov.fantasy_war.domain.competence;

import com.ynov.fantasy_war.domain.RequeteInvalideException;

public class IllegalKnownCompetenceException extends RequeteInvalideException {
    public IllegalKnownCompetenceException() {
        super("L'aventurier ne connait pas les compétences requises pour cette compétence !");
    }
}
