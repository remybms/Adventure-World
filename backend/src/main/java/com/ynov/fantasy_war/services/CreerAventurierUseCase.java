package com.ynov.fantasy_war.services;

import com.ynov.fantasy_war.domain.AventurierDomain;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
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
        AventurierDto aventurier = buildAventurier(aventurierPayload);
        aventurierRepository.save(aventurier);
        return aventurier;
    }

    private static @NonNull AventurierDto buildAventurier(AventurierPayload aventurierPayload) {
        AventurierDto aventurier = new AventurierDto();
        UUID id = UUID.randomUUID();
        AventurierMapper.fillAventurier(aventurierPayload, aventurier, id);
        return aventurier;
    }
}
