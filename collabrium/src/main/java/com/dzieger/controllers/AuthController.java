package com.dzieger.controllers;

import com.dzieger.exceptions.InvalidTokenException;
import com.dzieger.models.AppUser;
import com.dzieger.models.DTOs.LoginDTO;
import com.dzieger.models.DTOs.RequestRefreshTokenDTO;
import com.dzieger.models.DTOs.TokenDTO;
import com.dzieger.security.JwtUtil;
import com.dzieger.services.AuthService;
import com.dzieger.services.UserService;
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

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO credentials) {
        return ResponseEntity.ok(authService.login(credentials));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@RequestBody TokenDTO incomingTokenDTO) {
        return ResponseEntity.ok(authService.refresh(incomingTokenDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody TokenDTO tokenDTO) {
        return ResponseEntity.ok(authService.logout(tokenDTO));
    }

}
