package com.dzieger.services;

import com.dzieger.models.AppUser;
import com.dzieger.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserService is used to provide user services.
 *
 * @see com.dzieger.models.AppUser
 * @see com.dzieger.repositories.UserRepository
 *
 * @version 1.0
 */
@Service
public class UserService {

    /**
     * UserRepository
     */
    private final UserRepository userRepository;

    /**
     * Constructor
     *
     * @param userRepository The user repository
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * getUserByUsername
     *
     * This method is used to get a user by username.
     *
     * @param username The username of the user to get
     * @return The user
     */
    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}
