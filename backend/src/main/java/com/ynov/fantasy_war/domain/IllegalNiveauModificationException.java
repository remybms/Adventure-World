package com.ynov.fantasy_war.domain;

public class IllegalNiveauModificationException extends RuntimeException {
    public IllegalNiveauModificationException(int niveauActuel, int niveauCible) {
        super("Le nouveau niveau de l'aventurier est illégale. Actuel : " + niveauActuel + " ; cible : " + niveauCible);
    }
}
