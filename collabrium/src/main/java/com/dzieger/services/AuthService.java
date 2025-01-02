package com.dzieger.services;

import com.dzieger.exceptions.InvalidTokenException;
import com.dzieger.models.AppUser;
import com.dzieger.models.DTOs.LoginDTO;
import com.dzieger.models.DTOs.TokenDTO;
import com.dzieger.repositories.UserRepository;
import com.dzieger.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AllUserDetailsService allUserDetailsService;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, AllUserDetailsService allUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.allUserDetailsService = allUserDetailsService;
    }

    public TokenDTO login(LoginDTO loginDTO) {
        logger.info("Received login request");

        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) allUserDetailsService.loadUserByUsername(loginDTO.getUsername());
            logger.info("Auth Service: User details loaded for user: " + userDetails.getUsername());

            String token = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getTokenVersion());

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(token);

            logger.info("Login Success - Token generated for user: " + jwtUtil.extractUsername(token));
            return tokenDTO;
        } catch (AuthenticationException e) {
            logger.error("Login Failed - Invalid username or password");
            throw new InvalidTokenException("Login Failed - Invalid username or password");
        }
    }

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

            if(!allUserDetailsService.getIsDevProfile()) {
                incrementTokenVersion(userDetails);
            }


            String newToken = jwtUtil.generateToken(username, tokenVersion);

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(newToken);

            logger.info("Token refreshed for user: " + jwtUtil.extractUsername(newToken));
            return tokenDTO;
        } catch (Exception e) {
            logger.error("Token refresh failed", e);
            throw new InvalidTokenException("Token refresh failed", e);
        }
    }

    public String logout(TokenDTO incomingTokenDTO) {
        logger.info("Received logout request");

        try {
            String username = jwtUtil.extractUsername(incomingTokenDTO.getToken());
            if (username == null) {
                throw new InvalidTokenException("Logout Failed - Invalid token");
            }
            int tokenVersion = jwtUtil.extractTokenVersion(incomingTokenDTO.getToken());

            CustomUserDetails userDetails = (CustomUserDetails) allUserDetailsService.loadUserByUsername(username);

            jwtUtil.validateToken(incomingTokenDTO.getToken(), userDetails.getTokenVersion());

            logger.info("Token version before logout: " + userDetails.getTokenVersion());
            userDetails.setTokenVersion(userDetails.getTokenVersion() + 1);
            logger.info("Token version after logout: " + userDetails.getTokenVersion());

            logger.info("Token invalidated for user: " + jwtUtil.extractUsername(incomingTokenDTO.getToken()));
            return "Logout successful";
        } catch (Exception e) {
            logger.error("Logout failed", e);
            throw new InvalidTokenException("Logout failed", e);
        }
    }


    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void incrementTokenVersion(CustomUserDetails userDetails) {
        AppUser appUser = getUserByUsername(userDetails.getUsername());

        appUser.setTokenVersion(appUser.getTokenVersion() + 1);
        userRepository.save(appUser);

        logger.info("Token version incremented for user: " + userDetails.getUsername());
    }

}
