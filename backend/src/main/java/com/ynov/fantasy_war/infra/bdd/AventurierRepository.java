package com.ynov.fantasy_war.infra.bdd;

import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface AventurierRepository extends JpaRepository<AventurierEntity, UUID> {

}

