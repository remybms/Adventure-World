package com.ynov.fantasy_war.services.aventurier;

import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.ClasseEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ListerAventurierUseCase {

    private final AventurierRepository aventurierRepository;

    public List<AventurierDto> execute() {
        List<AventurierEntity> aventuriers = aventurierRepository.findAll();

        return aventuriers.stream()
                .map(this::toDto)
                .toList();
    }

    private AventurierDto toDto(AventurierEntity aventurier) {
        return AventurierMapper.toDto(aventurier);
    }
}
