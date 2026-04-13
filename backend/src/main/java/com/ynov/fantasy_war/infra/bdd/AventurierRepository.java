package com.ynov.fantasy_war.infra.bdd;

import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class AventurierRepository {
    private final Map<UUID, AventurierDto> aventuriers = new HashMap<>();
    public void save(AventurierDto aventurier) {
        aventuriers.put(aventurier.getId(), aventurier);
    }
    public Optional<AventurierDto> findById(UUID id) {
        return Optional.ofNullable(aventuriers.get(id));
    }
}

