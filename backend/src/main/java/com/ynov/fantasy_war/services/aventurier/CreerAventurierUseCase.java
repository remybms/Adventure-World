package com.ynov.fantasy_war.services.aventurier;

import com.ynov.fantasy_war.domain.AventurierDomain;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.ClasseEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class CreerAventurierUseCase {

    private final AventurierDomain aventurierDomain;
    private final AventurierRepository aventurierRepository;

    public AventurierDto execute(AventurierPayload aventurierPayload) {
        log.info("CreerAventurierUseCase execute");
        aventurierDomain.checkNiveauCreation(aventurierPayload.getNiveau());
        AventurierDto aventurierDto = buildAventurier(aventurierPayload);
        AventurierEntity aventurier = new AventurierEntity();
        AventurierDto.ClasseEnum classe = aventurierDto.getClasse();
        ClasseEntity classeEntity = ClasseEntity.valueOf(classe.name());
        aventurier.setClasse(classeEntity);
        aventurier.setNom(aventurierDto.getNom());
        aventurier.setDescription(aventurierDto.getDescription());
        aventurier.setNiveau(1);
        aventurier.setMental(aventurierDto.getMental());
        aventurier.setPerception(aventurierDto.getPerception());
        aventurier.setPhysique(aventurierDto.getPhysique());
        aventurierRepository.save(aventurier);
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

    private static @NonNull AventurierDto buildAventurier(AventurierPayload aventurierPayload) {
        AventurierDto aventurier = new AventurierDto();
        UUID id = UUID.randomUUID();
        AventurierMapper.fillAventurier(aventurierPayload, aventurier, id);
        return aventurier;
    }
}
