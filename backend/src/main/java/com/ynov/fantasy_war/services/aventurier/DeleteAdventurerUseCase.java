package com.ynov.fantasy_war.services.aventurier;

import com.ynov.fantasy_war.infra.bdd.AventurierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DeleteAdventurerUseCase {

    private final AventurierRepository aventurierRepository;

    public void execute(UUID id){
        aventurierRepository.deleteById(id);
    }


}
