package com.ynov.fantasy_war.services.users.jwt;

import com.ynov.fantasy_war.infra.bdd.UsersRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.ynov.fantasy_war.infra.bdd.entity.AppUser;

import java.util.List;

/**
 * Pont entre la base de données (AppUser) et Spring Security (UserDetails).
 *
 * <p>Quand Spring Security doit authentifier un utilisateur via username/password,
 * il appelle {@link #loadUserByUsername(String)} pour récupérer :</p>
 * <ul>
 *   <li>le username</li>
 *   <li>le password hash</li>
 *   <li>les autorités/roles</li>
 * </ul>
 *
 * <p>Ce service est utilisé par l'AuthenticationManager (login) et potentiellement par d'autres mécanismes.</p>
 */
@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UsersRepository repository;

    /**
     * Injection du repository via constructeur.
     *
     * @param repository accès DB des utilisateurs
     */
    public DbUserDetailsService(UsersRepository repository) {
        this.repository = repository;
    }

    /**
     * Charge un utilisateur pour Spring Security à partir du username.
     *
     * <p>Si l'utilisateur n'existe pas, on lève {@link UsernameNotFoundException},
     * ce qui fait échouer l'authentification (comportement attendu).</p>
     *
     * @param username username fourni lors du login
     * @return UserDetails (objet standard Spring Security)
     * @throws UsernameNotFoundException si l'utilisateur n'existe pas
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // On transforme notre rôle texte ("ROLE_USER") en GrantedAuthority pour Spring Security
        var authorities = List.of(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                authorities
        );
    }
}

