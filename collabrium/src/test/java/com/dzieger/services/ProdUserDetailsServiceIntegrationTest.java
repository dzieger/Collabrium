package com.dzieger.services;

import com.dzieger.collabrium.CollabriumApplication;
import com.dzieger.models.AppUser;
import com.dzieger.models.Role;
import com.dzieger.models.UserRole;
import com.dzieger.repositories.RoleRepository;
import com.dzieger.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CollabriumApplication.class)
@ActiveProfiles("test")
@Transactional
public class ProdUserDetailsServiceIntegrationTest {

    @Autowired
    private ProdUserDetailsService prodUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void loadUserByUsername_withValidData_returnsUserDetails() {

        Role role = new Role(null, "ROLE_ADMIN");
        role = roleRepository.save(role);
        AppUser user = new AppUser();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("password"));
        user.setEmail("email@email.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRoles(List.of(new UserRole(null, user, role)));

        userRepository.save(user);

        UserDetails userDetails = prodUserDetailsService.loadUserByUsername("admin");

        assertNotNull(userDetails);
        assertEquals("admin", userDetails.getUsername());
        assertTrue(passwordEncoder.matches("password", userDetails.getPassword()));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

    }

}
