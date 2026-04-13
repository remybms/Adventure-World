package com.ynov.fantasy_war.services;

import com.ynov.fantasy_war.domain.AventurierDomain;
import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ModifierAventurierUseCase {
    private final AventurierRepository aventurierRepository;
    private final AventurierDomain aventurierDomain;

    public AventurierDto execute(UUID id, AventurierPayload aventurierPayload) {
        AventurierDto aventurierDto = aventurierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aventurier", id));

        aventurierDomain .checkNiveauModification(aventurierDto.getNiveau(), aventurierPayload.getNiveau()) ;

        AventurierMapper.fillAventurier(aventurierPayload, aventurierDto, id);
        aventurierRepository.save(aventurierDto);
        return aventurierDto;
    }
}
