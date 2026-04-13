package com.ynov.fantasy_war.domain;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String type, UUID id) {
        super("La donnée de type [" + type + "] avec l'id [" + id + "] n'existe pas");
    }
}
