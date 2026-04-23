package com.ynov.fantasy_war.services.aventurier;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierPayload;

import java.util.UUID;

public class AventurierMapper {
    public static void fillAventurier(AventurierPayload aventurierPayload, AventurierDto aventurier, UUID id) {
        aventurier.setId(id);
        aventurier.setNom(aventurierPayload.getNom());
        aventurier.setDescription(aventurierPayload.getDescription());
        aventurier.setMental(aventurierPayload.getMental());
        aventurier.setPhysique(aventurierPayload.getPhysique());
        aventurier.setPerception(aventurierPayload.getPerception());
        aventurier.setClasse(AventurierDto.ClasseEnum.fromValue(aventurierPayload.getClasse().getValue()));
        aventurier.setNiveau(aventurierPayload.getNiveau());
    }
}