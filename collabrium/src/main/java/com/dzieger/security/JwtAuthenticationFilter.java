package com.dzieger.security;

import com.dzieger.exceptions.InvalidTokenException;
import com.dzieger.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JwtAuthenticationFilter is a custom filter for Spring Security.
 * It intercepts incoming requests to validate JWT tokens and sets
 * the authentication context for the request if the token is valid.
 *
 * This filter is executed once per request and integrates with the
 * Spring Security filter chain.
 *
 * Dependencies:
 * - JwtUtil: Provides token parsing and validation functionality.
 * - UserDetailsService: Loads user details from the database.
 * - AuthService: Handles token-related business logic.
 *
 * @version 1.0
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor
     *
     * @param jwtUtil          JwtUtil
     * @param userDetailsService UserDetailsService
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method intercepts incoming requests and checks for a JWT token in the authorization header.
     * If a token is found, it will validate the token and set the authentication context for the request.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param filter   FilterChain
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws
            IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");

        log.debug("Starting doFilterInternal");


        String username = null;
        String token = null;

        // Extract username and token from Authorization header

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            log.info("Extracting username and token from Authorization header");
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        // If username is found and the authentication context is not set, set the authentication context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            log.info("Setting authentication context");
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

            try {
                // Validate the token
                if (jwtUtil.validateToken(token, userDetails.getTokenVersion())) {
                    log.info("Token validated for username: {}", username);

                    // Extract authorities from token
                    List<String> authorities = jwtUtil.extractAuthorities(token);
                    List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();

                    // Set authentication context
                    var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    log.info("Authentication context set");
                }
            } catch (TokenExpiredException e) {
                log.warn("Token has expired: {}", e.getMessage());
            } catch (InvalidTokenException e) {
                log.warn("Invalid token provided: {}", e.getMessage());
            }
        }

        log.info("Proceeding with filter chain");
        filter.doFilter(request, response);
    }

}
