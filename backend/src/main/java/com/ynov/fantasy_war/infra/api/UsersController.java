package com.ynov.fantasy_war.infra.api;

import com.ynov.fantasy_war.infrastructure.web.openapi.api.AuthApi;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AuthRequest;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AuthResponse;
import com.ynov.fantasy_war.services.users.UsersUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController implements AuthApi {

    private final UsersUseCase usersUseCase;

    public UsersController(UsersUseCase usersUseCase){
        this.usersUseCase = usersUseCase;
    }

    @Override
    public AuthResponse connexion(AuthRequest authRequest){
        AuthResponse authResponse = usersUseCase.connexion(authRequest);
        return authResponse;
    }

    @Override
    public void deconnexion(){
        usersUseCase.deconnexion();
    }

    @Override
    public void inscription(AuthRequest authRequest){
        usersUseCase.inscription(authRequest);
    }
}
