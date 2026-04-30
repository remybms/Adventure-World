package com.ynov.fantasy_war.domain.competence;

import com.ynov.fantasy_war.domain.RequeteInvalideException;

public class IllegalLevelException extends RequeteInvalideException {
    public IllegalLevelException(int niveauMinimum) {
        super("Le niveau de l'aventurier n'est pas suffisant pour cette compétence ! Niveau Requis : " + niveauMinimum);
    }
}
