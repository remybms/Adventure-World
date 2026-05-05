package com.ynov.fantasy_war.domain.competence;

import com.ynov.fantasy_war.domain.ConflictException;

import java.util.List;
import java.util.UUID;

public class IllegalCompetenceModificationException extends ConflictException {
    public IllegalCompetenceModificationException(List<UUID> aventuriers) {
        super("La liste d'aventuriers suivantes bloque la modification de cette compétence : " + aventuriers);
    }
}
