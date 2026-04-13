package com.ynov.fantasy_war.services;

import com.ynov.fantasy_war.infra.bdd.AppUserRepository;
import com.ynov.fantasy_war.infra.bdd.entity.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(String username, String rawPassword) {
        if (repository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already used: " + username);
        }

        String hash = passwordEncoder.encode(rawPassword);

        repository.save(new AppUser(username, hash, "ROLE_USER"));
    }
}
