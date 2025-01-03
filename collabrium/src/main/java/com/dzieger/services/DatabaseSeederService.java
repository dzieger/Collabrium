package com.dzieger.services;

import com.dzieger.models.AppUser;
import com.dzieger.models.Role;
import com.dzieger.models.UserRole;
import com.dzieger.repositories.RoleRepository;
import com.dzieger.repositories.UserRepository;
import com.dzieger.repositories.UserRoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * DatabaseSeederService is used to seed the database with initial data.
 *
 * @see com.dzieger.models.AppUser
 * @see com.dzieger.models.Role
 * @see com.dzieger.models.UserRole
 * @see com.dzieger.repositories.RoleRepository
 * @see com.dzieger.repositories.UserRepository
 * @see com.dzieger.repositories.UserRoleRepository
 *
 * @version 1.0
 */
@Service
public class DatabaseSeederService {

    private static final Logger logger = Logger.getLogger(DatabaseSeederService.class.getName());
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor
     *
     * @param userRepository The UserRepository
     * @param roleRepository The RoleRepository
     * @param userRoleRepository The UserRoleRepository
     * @param passwordEncoder The PasswordEncoder
     */
    public DatabaseSeederService(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * seedDatabase is used to seed the database with initial data.
     *
     */
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

        if (userRepository.findByUsername("user").isEmpty()) {

            AppUser user = new AppUser();
            user.setEmail("N/A");
            user.setFirstName("N/A");
            user.setLastName("N/A");
            user.setEmail("N/A");
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setTokenVersion(0);
            userRepository.save(user);

            UserRole userRoleUser = new UserRole();
            userRoleUser.setUser(user);
            userRoleUser.setRole(roleUser);
            userRoleRepository.save(userRoleUser);

            logger.info("Created user user");
        }
    }

}
