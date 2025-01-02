package com.dzieger.services;

import com.dzieger.models.AppUser;
import com.dzieger.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class AllUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AllUserDetailsService.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final boolean isDevProfile;

    public AllUserDetailsService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.isDevProfile = "dev".equals(System.getProperty("spring.profiles.active", "dev"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (isDevProfile) {
            logger.info("Using DevUserDetailsService to load user: {}", username);
            return loadDevUser(username);
        } else {
            logger.info("Using ProdUserDetailsService to load user: {}", username);
            return loadProdUser(username);
        }
    }

    private UserDetails loadDevUser(String username) {
        logger.info("Using DevUserDetailsService to load user: {}", username);

        if ("admin".equals(username)) {
            CustomUserDetails userDetails = new CustomUserDetails(
                    "admin",
                    passwordEncoder.encode("password"),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")),
                    0
            );
            logger.info("Loaded user: {}", username);
            return userDetails;
        } else if ("user".equals(username)) {
            CustomUserDetails userDetails = new CustomUserDetails(
                    "user",
                    passwordEncoder.encode("password"),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                    0
            );
            logger.info("Loaded user: {}", username);
            return userDetails;
        }



        throw new UsernameNotFoundException("User not found: " + username);
    }

    private UserDetails loadProdUser(String username) {
        logger.info("Using ProdUserDetailsService to load user: {}", username);

        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new CustomUserDetails(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                        .collect(Collectors.toList()),
                appUser.getTokenVersion()
        );
    }

    public boolean getIsDevProfile() {
        return isDevProfile;
    }
}
