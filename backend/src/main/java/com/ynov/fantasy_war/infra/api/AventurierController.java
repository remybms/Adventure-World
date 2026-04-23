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
    private final GetAdventurerByIdUseCase getAdventurerByIdUseCase;
    private final DeleteAdventurerUseCase deleteAdventurerUseCase;
    private final ListerAventurierUseCase listerAventurierUseCase;

    public AventurierController(CreerAventurierUseCase creerAventurierUseCase, ModifierAventurierUseCase modifierAventurierUseCase, GetAdventurerByIdUseCase getAdventurerByIdUseCase, DeleteAdventurerUseCase deleteAdventurerUseCase, ListerAventurierUseCase listerAventurierUseCase) {
        this.creerAventurierUseCase = creerAventurierUseCase;
        this.modifierAventurierUseCase = modifierAventurierUseCase;
        this.getAdventurerByIdUseCase = getAdventurerByIdUseCase;
        this.deleteAdventurerUseCase = deleteAdventurerUseCase;
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
        AventurierDto aventurierDto = getAdventurerByIdUseCase.execute(id);
        return aventurierDto;
    }

    @Override
    public void supprimerAventurier(UUID id) {
        deleteAdventurerUseCase.execute(id);
    }
}

