package com.ynov.fantasy_war.domain;

import org.springframework.stereotype.Component;

@Component
public class AventurierDomain {

    public void checkNiveauCreation(int niveau) {
        if (niveau != 1) {
            throw new IllegalNiveauCreationException(niveau);
        }
    }

    public void checkNiveauModification(int niveauInitial, int niveauCible) {
        if (niveauInitial > niveauCible) {
            throw new IllegalNiveauModificationException(niveauInitial, niveauCible);
        }
        if (niveauCible > niveauInitial + 1) {
            throw new IllegalNiveauModificationException(niveauInitial, niveauCible);
        }
    }
}
