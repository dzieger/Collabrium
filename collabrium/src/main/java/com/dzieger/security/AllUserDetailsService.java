package com.dzieger.security;

import com.dzieger.models.AppUser;
import com.dzieger.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AllUserDetailsService is used to load user details from the database for Spring Security.
 *
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see com.dzieger.models.AppUser
 * @see com.dzieger.security.CustomUserDetails
 *
 * @version 1.0
 */
@Service
public class AllUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AllUserDetailsService.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * Default constructor
     *
     * @param passwordEncoder the password encoder
     * @param userRepository the user repository
     */
    public AllUserDetailsService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * loadUserByUsername
     *
     * This method is used to load a user by their username.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user: {}", username);

        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new CustomUserDetails(appUser);
    }
}
