package com.ynov.fantasy_war.infra.api;


import com.ynov.fantasy_war.infrastructure.web.openapi.api.AventuriersApi;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierPayload;
import com.ynov.fantasy_war.services.CreerAventurierUseCase;
import com.ynov.fantasy_war.services.ModifierAventurierUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@RestController
public class AventurierController implements AventuriersApi {

    private final CreerAventurierUseCase creerAventurierUseCase;
    private final ModifierAventurierUseCase modifierAventurierUseCase;

    public AventurierController(CreerAventurierUseCase creerAventurierUseCase, ModifierAventurierUseCase modifierAventurierUseCase) {
        this.creerAventurierUseCase = creerAventurierUseCase;
        this.modifierAventurierUseCase = modifierAventurierUseCase;
    }


    @Override
    public AventurierDto creerAventurier(AventurierPayload aventurierPayload) {
        log.info("AventurierController.creerAventurier: {}", aventurierPayload);
        AventurierDto aventurier = creerAventurierUseCase.execute(aventurierPayload);
        return aventurier;
    }

    @Override
    public List<AventurierDto> listerAventuriers() {
        return List.of();
    }

    @Override
    public AventurierDto modifierAventurier(UUID id, AventurierPayload aventurierPayload) {
        AventurierDto aventurierDto = modifierAventurierUseCase.execute(id, aventurierPayload);
        return aventurierDto;
    }

    @Override
    public AventurierDto obtenirAventurier(UUID id) {
        return null;
    }

    @Override
    public void supprimerAventurier(UUID id) {

    }
}

