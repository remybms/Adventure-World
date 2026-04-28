package com.ynov.fantasy_war.services.aventurier;

import com.ynov.fantasy_war.domain.AventurierDomain;
import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AventurierEntity;
import com.ynov.fantasy_war.infra.bdd.entity.ClasseEntity;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierDto;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AventurierPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ModifierAventurierUseCase {
    private final AventurierRepository aventurierRepository;
    private final AventurierDomain aventurierDomain;

    public AventurierDto execute(UUID id, AventurierPayload aventurierPayload) {
        AventurierEntity aventurier = aventurierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aventurier", id));

        aventurierDomain.checkNiveauModification(aventurier.getNiveau(), aventurierPayload.getNiveau()) ;
        aventurier.setNom(aventurierPayload.getNom());
        aventurier.setDescription(aventurierPayload.getDescription());
        aventurier.setNiveau(aventurierPayload.getNiveau());
        aventurier.setPhysique(aventurierPayload.getPhysique());
        aventurier.setMental(aventurierPayload.getMental());
        aventurier.setPerception(aventurierPayload.getPerception());

        if (aventurierPayload.getClasse() != null) {
            AventurierPayload.ClasseEnum classe = aventurierPayload.getClasse();
            ClasseEntity classeEntity = ClasseEntity.valueOf(classe.name());
            aventurier.setClasse(classeEntity);
        }

        aventurierRepository.save(aventurier);
        AventurierDto aventurierDto = new AventurierDto();
        aventurierDto.setNom(aventurier.getNom());
        aventurierDto.setDescription(aventurier.getDescription());
        aventurierDto.setNiveau(aventurier.getNiveau());
        ClasseEntity classeEntity = aventurier.getClasse();
        AventurierDto.ClasseEnum classe = AventurierDto.ClasseEnum.valueOf(classeEntity.name());
        aventurierDto.setClasse(classe);
        aventurierDto.setPhysique(aventurier.getPhysique());
        aventurierDto.setMental(aventurier.getMental());
        aventurierDto.setPerception(aventurier.getPerception());
        AventurierMapper.fillAventurier(aventurierPayload, aventurierDto, id);
        aventurierRepository.save(aventurier);

        return aventurierDto;
    }


}
