package com.dzieger.security;

import com.dzieger.exceptions.InvalidTokenException;
import com.dzieger.exceptions.TokenExpiredException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Utility class for handling JWT tokens
 *
 * This class provides methods for generating, validating, and extracting information from JWT tokens.
 *
 *
 * @version 1.0
 */
@Component
public class JwtUtil {

    /**
     * Default constructor
     */
    public JwtUtil() {
        // Default constructor
    }

    /**
     * Logger
     */
    private final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * Secret for creating the SecretKey
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Expiration time for the JWT token
     */
    @Value("${jwt.expiration}")
    private int expiration;

    /**
     * Secret key for signing the JWT token
     */
    private Key SECRET_KEY;

    /**
     * Initializes the secret key using the secret string passed in the application.properties file
     */
    @PostConstruct
    public void init() {
        try {
            if (secret == null || secret.isEmpty()) {
                throw new IllegalArgumentException("JWT secret cannot be null or empty");
            }
            this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
            log.info("Secret key initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize secret key", e);
            throw new IllegalStateException("Failed to initialize secret key", e);
        }
    }

    /**
     * Extracts the username from the token
     *
     * @param token JWT token
     * @return Username
     */
    public String extractUsername(String token) {
        log.info("Extracting username from token");
        try {
            Claims claims = extractAllClaims(token);
            String username = claims.getSubject();
            log.debug("Username extracted: {}", username);
            return claims.getSubject();
        } catch (TokenExpiredException e) {
            log.error("Token has expired", e);
            throw new TokenExpiredException("Token has expired", e);
        } catch (JwtException e) {
            log.error("Unable to extract username: Invalid Token", e);
            throw new InvalidTokenException("Unable to extract username: Invalid Token", e);
        }
    }

    /**
     * Extracts the token version from the token
     *
     * @param token JWT token
     * @return Token version
     */
    public int extractTokenVersion(String token) {
        log.info("Extracting token version from token");
        try {
            Claims claims = extractAllClaims(token);
            return claims.get("tokenVersion", Integer.class);
        } catch (JwtException e) {
            throw new InvalidTokenException("Unable to extract token version: Invalid Token", e);
        }
    }

    /**
     * Extracts a claim from the token
     *
     * @param token          JWT token
     * @param claimsResolver Function to resolve the claim
     * @param <T>            Type of the claim
     * @return Claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        log.info("Extracting claim from token");
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the token
     *
     * @param token JWT token
     * @return All claims
     */
    private Claims extractAllClaims(String token) {
        log.info("Extracting all claims from token");
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    /**
     * Generates a JWT token
     *
     * @param username     Username
     * @param tokenVersion Token version
     * @param authorities  Authorities
     * @return JWT token
     */
    public String generateToken(String username, int tokenVersion, Collection<? extends GrantedAuthority> authorities) {
        log.debug("Generating token for user: {}", username);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);
        log.debug("Token expiration date: {}", expirationDate);

        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("tokenVersion", tokenVersion)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT token
     *
     * @param token        JWT token
     * @param tokenVersion Token version
     * @return True if the token is valid, false otherwise
     */
    public boolean validateToken(String token, int tokenVersion) {
        log.info("Validating token");
        Claims claims = extractAllClaims(token);
        int tokenVersionClaim = claims.get("tokenVersion", Integer.class);

        if (new Date().after(extractExpiration(token))) {
            throw new TokenExpiredException("Token has expired");
        }

        if (tokenVersionClaim != tokenVersion) {
            throw new InvalidTokenException("Token version mismatch");
        }
        return true;
    }

    /**
     * Extracts the authorities from the token
     *
     * @param token JWT token
     * @return Authorities
     */
    public List<String> extractAuthorities(String token) {
        log.info("Extracting authorities from token");
        Claims claims = extractAllClaims(token);
        return claims.get("authorities", List.class);
    }


    /**
     * Extracts the expiration date from the token
     *
     * @param token JWT token
     * @return Expiration date
     */
    private Date extractExpiration(String token) {
        log.info("Extracting expiration date from token");
        return extractClaim(token, Claims::getExpiration);
    }

}
