package com.dzieger.services;

import com.dzieger.models.AppUser;
import com.dzieger.models.Role;
import com.dzieger.models.UserRole;
import com.dzieger.repositories.RoleRepository;
import com.dzieger.repositories.UserRepository;
import com.dzieger.repositories.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class DatabaseSeederService {

    private static final Logger logger = Logger.getLogger(DatabaseSeederService.class.getName());
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeederService(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void seedDatabase() {
        logger.info("Seeding database");

        Role roleAdmin = roleRepository.findByNameIgnoreCase("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

        Role roleUser = roleRepository.findByNameIgnoreCase("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER")));


        if (userRepository.findByUsername("admin").isEmpty()) {

            AppUser admin = new AppUser();
            admin.setEmail("N/A");
            admin.setFirstName("N/A");
            admin.setLastName("N/A");
            admin.setEmail("N/A");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setTokenVersion(0);
            userRepository.save(admin);

            UserRole userRoleAdmin = new UserRole();
            userRoleAdmin.setUser(admin);
            userRoleAdmin.setRole(roleAdmin);
            userRoleRepository.save(userRoleAdmin);

            logger.info("Created admin user");
        }
    }

}
