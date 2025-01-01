package com.dzieger.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevUserDetailsService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(DevUserDetailsService.class);

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    public DevUserDetailsService(PasswordEncoder passwordEncoder) {
        logger.info("Initiliazing InMemoryUserDetailsManager for development");

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        this.inMemoryUserDetailsManager = new InMemoryUserDetailsManager(admin, user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return inMemoryUserDetailsManager.loadUserByUsername(username);
    }

}
