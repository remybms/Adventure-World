package com.ynov.fantasy_war.domain.competence;

public class IllegalCaracteristiquePointsException extends RuntimeException {
    public IllegalCaracteristiquePointsException(String caracteristique, int requiredPoints) {
        super("La caractéristique " + caracteristique + " de l'aventurier n'a pas la quantité de points requise ! Points requis : " + requiredPoints);
    }
}
