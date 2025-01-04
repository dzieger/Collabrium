package com.dzieger.security;

import com.dzieger.models.AppUser;
import com.dzieger.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * AllUserDetailsService is used to load user details from the database for Spring Security.
 * Retrieves user data from the database and creates a UserDetails object for authentication.
 *
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see com.dzieger.models.AppUser
 * @see com.dzieger.security.CustomUserDetails
 *
 * @version 1.0
 */
@Service
public class AllUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AllUserDetailsService.class);
    private final UserRepository userRepository;

    /**
     * Default constructor
     *
     * @param userRepository the user repository
     */
    public AllUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username from the database.
     *
     * This method is called by Spring Security to load a user by username for authentication.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user: {}", username);

        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new CustomUserDetails(appUser);
    }
}
