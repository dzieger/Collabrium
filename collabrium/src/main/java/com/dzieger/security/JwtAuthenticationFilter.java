package com.dzieger.security;

import com.dzieger.exceptions.InvalidTokenException;
import com.dzieger.exceptions.TokenExpiredException;
import com.dzieger.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * **************************** JwtAuthenticationFilter **********************************
 *
 * This class is a filter that intercepts incoming requests and checks for a JWT token in the authorization header.
 * If a token is found, it will validate the token and set the authentication context for the request.
 *
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;

    /**
     * Constructor
     *
     * @param jwtUtil          JwtUtil
     * @param userDetailsService UserDetailsService
     * @param authService      AuthService
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
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

        logger.info("Starting doFilterInternal");


        String username = null;
        String token = null;

        // Extract username and token from Authorization header

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            logger.info("Extracting username and token from Authorization header");
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        // If username is found and the authentication context is not set, set the authentication context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            logger.info("Setting authentication context");
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

            try {
                // Validate the token
                if (jwtUtil.validateToken(token, userDetails.getTokenVersion())) {
                    logger.info("Token validated for username: {}", username);

                    // Extract authorities from token
                    List<String> authorities = jwtUtil.extractAuthorities(token);
                    List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();

                    // Set authentication context
                    var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    logger.info("Authentication context set");
                }
            } catch (TokenExpiredException e) {
                logger.warn("Token has expired");
            } catch (InvalidTokenException e) {
                logger.warn("Invalid token");
            }
        }

        logger.info("Proceeding with filter chain");
        filter.doFilter(request, response);
    }

}
