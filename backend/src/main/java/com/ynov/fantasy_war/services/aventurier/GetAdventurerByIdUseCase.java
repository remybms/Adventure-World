package com.ynov.fantasy_war.services.aventurier;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.ClasseEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GetAdventurerByIdUseCase {
    private final AventurierRepository aventurierRepository;

    public AventurierDto execute(UUID id){
        AventurierEntity aventurier = aventurierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aventurier", id));
        AventurierDto aventurierDto = new AventurierDto();
        aventurierDto.setId(aventurier.getId());
        aventurierDto.setNom(aventurier.getNom());
        aventurierDto.setDescription(aventurier.getDescription());
        aventurierDto.setNiveau(aventurier.getNiveau());
        ClasseEntity classeEntity = aventurier.getClasse();
        AventurierDto.ClasseEnum classe = AventurierDto.ClasseEnum.valueOf(classeEntity.name());
        aventurierDto.setClasse(classe);
        aventurierDto.setPhysique(aventurier.getPhysique());
        aventurierDto.setMental(aventurier.getMental());
        aventurierDto.setPerception(aventurier.getPerception());
        return aventurierDto;
    }
}
