package com.dzieger.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig sets up the Spring Security configuration for the application.
 * It defines security policies, such as endpoint access rules, authentication mechanisms,
 * and password encoding strategies.
 *
 * An authentication filter (JwtAuthenticationFilter) is added to handle JWT-based authentication.
 *
 * @version 1.0
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * SecurityConfig constructor
     */
    public SecurityConfig() {
        // Default constructor
    }

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    // TODO: Add and externalize all public endpoints

    /**
     * SecurityFilterChain
     * - Disables CSRF protection
     * - Allows public access to Swagger UI and API documentation
     * - Allows public access to the authentication endpoints
     * - Requires authentication for all other requests
     *
     * @param http HttpSecurity
     * @param jwtAuthenticationFilter JwtAuthenticationFilter
     * @return SecurityFilterChain
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        log.info("Configuring SecurityFilterChain");

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/api-docs/**", "/v3/api-docs/**","/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        log.info("SecurityFilterChain configured");
        return http.build();
    }

    /**
     * PasswordEncoder
     * - Creates a BCryptPasswordEncoder bean
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Creating BCryptPasswordEncoder bean");
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager
     * - Creates an AuthenticationManager bean
     *
     * @param configuration AuthenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }




}
