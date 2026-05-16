package com.ynov.fantasy_war.domain.competence;

import com.ynov.fantasy_war.domain.RequeteInvalideException;

public class IllegalClasseException extends RequeteInvalideException {
    public IllegalClasseException(String requiredClasse) {
        super("La classe de l'aventurier ne correspond pas à la classe requise pour cette compétence ! Classe requise : " + requiredClasse);
    }
}
