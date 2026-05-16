package com.ynov.fantasy_war.services.aventurier;

import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.ClasseEntity;
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

    public static AventurierDto toDto(AventurierEntity aventurier) {
        AventurierDto aventurierResult = new AventurierDto();
        aventurierResult.setId(aventurier.getId());
        aventurierResult.setNom(aventurier.getNom());
        aventurierResult.setDescription(aventurier.getDescription());
        aventurierResult.setNiveau(aventurier.getNiveau());
        ClasseEntity classeEntityResult = aventurier.getClasse();
        AventurierDto.ClasseEnum classeResult = AventurierDto.ClasseEnum.valueOf(classeEntityResult.name());
        aventurierResult.setClasse(classeResult);
        aventurierResult.setPhysique(aventurier.getPhysique());
        aventurierResult.setMental(aventurier.getMental());
        aventurierResult.setPerception(aventurier.getPerception());
        return aventurierResult;
    }
}