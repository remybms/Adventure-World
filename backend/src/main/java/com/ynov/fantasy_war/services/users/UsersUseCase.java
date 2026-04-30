package com.ynov.fantasy_war.services.users;


import com.ynov.fantasy_war.infra.bdd.UsersRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AppUser;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AuthRequest;
import com.ynov.fantasy_war.infrastructure.web.openapi.dto.AuthResponse;
import com.ynov.fantasy_war.services.users.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsersUseCase {

    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse connexion(AuthRequest authRequest){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtService.generate(auth));
        return authResponse;
    }

    public void deconnexion(){

    }

    public void inscription(AuthRequest authRequest){
        AppUser user = new AppUser(authRequest.getUsername(), passwordEncoder.encode(authRequest.getPassword()), "VIEWER");
        usersRepository.save(user);
    }
}
