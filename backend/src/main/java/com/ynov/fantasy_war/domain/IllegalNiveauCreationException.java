package com.ynov.fantasy_war.domain;

public class IllegalNiveauCreationException extends RequeteInvalideException {
    public IllegalNiveauCreationException(int niveau) {
        super("Un aventurier nouvellement créé doit avoir un niveau de 1. Le niveau actuel est : " + niveau);
    }
}
