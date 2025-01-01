package com.dzieger.services;

import com.dzieger.models.AppUser;
import com.dzieger.repositories.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Profile("prod")
public class ProdUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ProdUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                                .collect(Collectors.toList())))
                .orElseThrow(() -> new UsernameNotFoundException("AppUser not found with username: " + username));
    }
}
