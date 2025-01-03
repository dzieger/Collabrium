package com.dzieger.services;

import com.dzieger.exceptions.InvalidTokenException;
import com.dzieger.models.AppUser;
import com.dzieger.models.DTOs.LoginDTO;
import com.dzieger.models.DTOs.TokenDTO;
import com.dzieger.models.DTOs.UserRegisterDTO;
import com.dzieger.models.Role;
import com.dzieger.models.UserRole;
import com.dzieger.repositories.RoleRepository;
import com.dzieger.repositories.UserRepository;
import com.dzieger.repositories.UserRoleRepository;
import com.dzieger.security.AllUserDetailsService;
import com.dzieger.security.CustomUserDetails;
import com.dzieger.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * ****************************** Auth Service *******************************
 *
 * The authService handles all authentication business logic such as:
 * - Login
 * - Refreshing a token
 * - Logging out
 * - Registering a new user
 *
 * The service uses the JwtUtil to generate and validate JWT tokens.
 * @see com.dzieger.security.JwtUtil
 *
 * The service uses the UserRepository to interact with the database.
 * @see com.dzieger.repositories.UserRepository
 *
 * The service uses the RoleRepository to interact with the database.
 * @see com.dzieger.repositories.RoleRepository
 *
 * The service uses the UserRoleRepository to interact with the database.
 * @see com.dzieger.repositories.UserRoleRepository
 *
 * The service uses the AllUserDetailsService to load user details.
 * @see AllUserDetailsService
 *
 * The service uses the PasswordEncoder to encode passwords.
 * @see org.springframework.security.crypto.password.PasswordEncoder
 *
 * ****************************************************************************
 *
 * Methods:
 * - login(LoginDTO loginDTO): TokenDTO
 *
 * - refresh(TokenDTO incomingTokenDTO): TokenDTO
 *
 * - logout(TokenDTO incomingTokenDTO): String
 *
 * - register(UserRegisterDTO userRegisterDTO): String
 *
 */

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final AllUserDetailsService allUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, AllUserDetailsService allUserDetailsService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.allUserDetailsService = allUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Login to the application
     *
     * @param loginDTO {String username, String password} - User credentials
     * @return TokenDTO {String token} - Token generated for the user
     * @throws InvalidTokenException - If the username or password is invalid
     */

    public TokenDTO login(LoginDTO loginDTO) {
        logger.info("Received login request");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) allUserDetailsService.loadUserByUsername(loginDTO.getUsername());
            logger.info("Auth Service: User details loaded for user: {}", userDetails.getUsername());

            String token = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getTokenVersion(), userDetails.getAuthorities());

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(token);

            logger.info("Login Success - Token generated for user: {}", loginDTO.getUsername());
            return tokenDTO;
        } catch (AuthenticationException e) {
            logger.error("Login Failed - Invalid username or password");
            throw new InvalidTokenException("Login Failed - Invalid username or password");
        }
    }

    /**
     * Refresh a JWT token
     * @param incomingTokenDTO {String token} - Token to be refreshed
     * @return TokenDTO {String token} - New token generated for the user
     * @throws InvalidTokenException - If the token is invalid
     * @throws UsernameNotFoundException - If the username is not found
     */

    public TokenDTO refresh(TokenDTO incomingTokenDTO) {
        logger.info("Received refresh token request");

        try {
            String username = jwtUtil.extractUsername(incomingTokenDTO.getToken());
            if (username == null) {
                throw new InvalidTokenException("Refresh Token Failed - Invalid token");
            }
            int tokenVersion = jwtUtil.extractTokenVersion(incomingTokenDTO.getToken());

            CustomUserDetails userDetails = (CustomUserDetails) allUserDetailsService.loadUserByUsername(username);

            jwtUtil.validateToken(incomingTokenDTO.getToken(), userDetails.getTokenVersion());

            incrementTokenVersion(userDetails);

            String newToken = jwtUtil.generateToken(username, tokenVersion, userDetails.getAuthorities());

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(newToken);

            logger.info("Token refreshed for user: {}", jwtUtil.extractUsername(newToken));
            return tokenDTO;
        } catch (Exception e) {
            throw new InvalidTokenException("Token refresh failed", e);
        }
    }

    /**
     * Logout of the application
     * @param incomingTokenDTO {String token} - Token to be invalidated
     * @return String - Logout message
     * @throws InvalidTokenException - If the token is invalid
     */
    public String logout(TokenDTO incomingTokenDTO) {
        logger.info("Received logout request");

        try {
            String username = jwtUtil.extractUsername(incomingTokenDTO.getToken());
            if (username == null) {
                throw new InvalidTokenException("Logout Failed - Invalid token");
            }

            CustomUserDetails userDetails = (CustomUserDetails) allUserDetailsService.loadUserByUsername(username);

            jwtUtil.validateToken(incomingTokenDTO.getToken(), userDetails.getTokenVersion());

            incrementTokenVersion(userDetails);

            logger.info("Token invalidated for user: {}", username);
            return "Logout successful";
        } catch (Exception e) {
            throw new InvalidTokenException("Logout failed", e);
        }
    }

    /**
     * Register a new user
     * @param userRegisterDTO {String username, String password, String email, String firstName, String lastName} - User details
     * @return String - Registration message
     * @throws IllegalArgumentException - If the username or email is already taken
     */
    public String register(UserRegisterDTO userRegisterDTO) {
        logger.info("Received register request");

        if (isUsernameTaken(userRegisterDTO.getUsername())) {
            logger.error("Register Failed - Username already taken");
            throw new IllegalArgumentException("Register Failed - Username already taken");
        }

        if (isEmailTaken(userRegisterDTO.getEmail())) {
            logger.error("Register Failed - Email already taken");
            throw new IllegalArgumentException("Register Failed - Email already taken");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(userRegisterDTO.getUsername());
        appUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        appUser.setEmail(userRegisterDTO.getEmail());
        appUser.setFirstName(userRegisterDTO.getFirstName());
        appUser.setLastName(userRegisterDTO.getLastName());

        userRepository.save(appUser);

        Role userRole = roleRepository.findByNameIgnoreCase("USER").orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        UserRole userRoleMapping = new UserRole();
        userRoleMapping.setUser(appUser);
        userRoleMapping.setRole(userRole);
        userRoleRepository.save(userRoleMapping);

        logger.info("Register Success - User registered: {}", appUser.getUsername());
        return "Register Success - User registered: " + appUser.getUsername();
    }


    // Helper methods

    // Check if the username is already taken
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Check if the email is already taken
    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Increment the token version for a user
    /**
     * Increment the token version for a user
     * @param userDetails - User details
     */
    public void incrementTokenVersion(CustomUserDetails userDetails) {
        AppUser appUser = getUserByUsername(userDetails.getUsername());

        appUser.setTokenVersion(appUser.getTokenVersion() + 1);
        userRepository.save(appUser);

        logger.info("Token version incremented for user: {}", userDetails.getUsername());
    }

}
