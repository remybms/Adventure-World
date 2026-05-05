package com.ynov.fantasy_war.infra.api;


import com.ynov.fantasy_war.infrastructure.web.openapi.api.AventuriersApi;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierPayload;
import com.ynov.fantasy_war.services.aventurier.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class AventurierController implements AventuriersApi {

    private final CreerAventurierUseCase creerAventurierUseCase;
    private final ModifierAventurierUseCase modifierAventurierUseCase;
    private final ObtenirAventurierParIdUseCase obtenirAventurierParIdUseCase;
    private final SupprimerAventurierUseCase supprimerAventurierUseCase;
    private final ListerAventurierUseCase listerAventurierUseCase;

    public AventurierController(CreerAventurierUseCase creerAventurierUseCase, ModifierAventurierUseCase modifierAventurierUseCase, ObtenirAventurierParIdUseCase obtenirAventurierParIdUseCase, SupprimerAventurierUseCase supprimerAventurierUseCase, ListerAventurierUseCase listerAventurierUseCase) {
        this.creerAventurierUseCase = creerAventurierUseCase;
        this.modifierAventurierUseCase = modifierAventurierUseCase;
        this.obtenirAventurierParIdUseCase = obtenirAventurierParIdUseCase;
        this.supprimerAventurierUseCase = supprimerAventurierUseCase;
        this.listerAventurierUseCase = listerAventurierUseCase;
    }


    @Override
    public AventurierDto creerAventurier(AventurierPayload aventurierPayload) {
        log.info("AventurierController.creerAventurier: {}", aventurierPayload);
        AventurierDto aventurier = creerAventurierUseCase.execute(aventurierPayload);
        return aventurier;
    }

    @Override
    public List<AventurierDto> listerAventuriers() {
        return listerAventurierUseCase.execute();
    }

    @Override
    public AventurierDto modifierAventurier(UUID id, AventurierPayload aventurierPayload) {
        AventurierDto aventurierDto = modifierAventurierUseCase.execute(id, aventurierPayload);
        return aventurierDto;
    }

    @Override
    public AventurierDto obtenirAventurier(UUID id) {
        AventurierDto aventurierDto = obtenirAventurierParIdUseCase.execute(id);
        return aventurierDto;
    }

    @Override
    public void supprimerAventurier(UUID id) {
        supprimerAventurierUseCase.execute(id);
    }
}

