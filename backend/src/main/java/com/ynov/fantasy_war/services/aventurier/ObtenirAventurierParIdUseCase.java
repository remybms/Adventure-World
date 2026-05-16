package com.ynov.fantasy_war.services.aventurier;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ObtenirAventurierParIdUseCase {
    private final AventurierRepository aventurierRepository;

    public AventurierDto execute(UUID id){
        AventurierEntity aventurier = aventurierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aventurier", id));
        AventurierDto aventurierDto = AventurierMapper.toDto(aventurier);
        return aventurierDto;
    }
}
