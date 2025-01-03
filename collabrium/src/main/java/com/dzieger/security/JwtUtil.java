package com.dzieger.security;

import com.dzieger.exceptions.InvalidTokenException;
import com.dzieger.exceptions.TokenExpiredException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);


    /**
     * Secret key used to sign the JWT token
     */
    private Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    /**
     * Expiration time for the token in milliseconds
     */
    private final long expiration = 3600000; // 1 hour

    /**
     * Extracts the username from the token
     *
     * @param token JWT token
     * @return Username
     */
    public String extractUsername(String token) {
        logger.info("Extracting username from token");
        try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (JwtException e) {
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
        logger.info("Extracting token version from token");
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
        logger.info("Extracting claim from token");
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the token
     *
     * @param token JWT token
     * @return All claims
     */
    private Claims extractAllClaims(String token) {
        logger.info("Extracting all claims from token");
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
        logger.info("Generating token for user: {}", username);
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("tokenVersion", tokenVersion)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * @deprecated Use {@link #validateToken(String, int)} instead
     * Validates a JWT token
     *
     * @param token JWT token
     *
     * @return True if the token is valid, false otherwise
     */
    @Deprecated(since = "Jan 02 2025", forRemoval = true)
    private boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("Token has expired");
        } catch (JwtException e) {
            throw new InvalidTokenException("Token is invalid", e);
        }
    }

    /**
     * Validates a JWT token
     *
     * @param token        JWT token
     * @param tokenVersion Token version
     * @return True if the token is valid, false otherwise
     */
    public boolean validateToken(String token, int tokenVersion) {
        logger.info("Validating token");
        Claims claims = extractAllClaims(token);
        int tokenVersionClaim = claims.get("tokenVersion", Integer.class);

        if (isTokenExpired(token)) {
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
        logger.info("Extracting authorities from token");
        Claims claims = extractAllClaims(token);
        return claims.get("authorities", List.class);
    }

    /**
     * Checks if the token is expired
     *
     * @param token JWT token
     * @return True if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        logger.info("Checking if token is expired");
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the token
     *
     * @param token JWT token
     * @return Expiration date
     */
    private Date extractExpiration(String token) {
        logger.info("Extracting expiration date from token");
        return extractClaim(token, Claims::getExpiration);
    }

}
