package com.dzieger.services;

import com.dzieger.models.AppUser;
import com.dzieger.models.Role;
import com.dzieger.models.UserRole;
import com.dzieger.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProdUserDetailsServiceTest {

    private UserRepository userRepository;
    private ProdUserDetailsService prodUserDetailsService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        prodUserDetailsService = new ProdUserDetailsService(userRepository);
    }

    @Test
    void loadUserByUsername_validUser_returnsUserDetails() {

        Role role = new Role(null, "ROLE_USER");
        AppUser user = new AppUser();
        user.setUsername("user");
        user.setPassword("password");
        user.setRoles(List.of(new UserRole(null, user, role)));

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        UserDetails userDetails = prodUserDetailsService.loadUserByUsername("user");

        assertNotNull(userDetails);
        assertEquals("user", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

    }

    @Test
    void loadUserByUsername_invalidUser_throwsUsernameNotFoundException() {

        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> prodUserDetailsService.loadUserByUsername("user"));

    }

}
