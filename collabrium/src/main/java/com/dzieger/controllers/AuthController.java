package com.dzieger.controllers;

import com.dzieger.models.DTOs.LoginDTO;
import com.dzieger.models.DTOs.TokenDTO;
import com.dzieger.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO credentials) {
        logger.info("Received login request");
        try {
            String username = credentials.getUsername();
            String password = credentials.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(jwtUtil.generateToken(username));
            logger.info("Login successful");
            return ResponseEntity.ok(tokenDTO);
        } catch (AuthenticationException e) {
            logger.error("Login failed", e);
            return ResponseEntity.status(401).build();
        }
    }

}
