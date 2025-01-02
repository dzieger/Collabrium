package com.dzieger.security;

import com.dzieger.exceptions.InvalidTokenException;
import com.dzieger.exceptions.TokenExpiredException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expiration = 3600000; // 1 hour

    public String extractUsername(String token) {
        logger.info("Extracting username from token");
        try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (JwtException e) {
            throw new InvalidTokenException("Unable to extract username: Invalid Token", e);
        }
    }

    public int extractTokenVersion(String token) {
        logger.info("Extracting token version from token");
        try {
            Claims claims = extractAllClaims(token);
            return claims.get("tokenVersion", Integer.class);
        } catch (JwtException e) {
            throw new InvalidTokenException("Unable to extract token version: Invalid Token", e);
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        logger.info("Extracting claim from token");
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        logger.info("Extracting all claims from token");
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(String username, int tokenVersion, Collection<? extends GrantedAuthority> authorities) {
        logger.info("Generating token for user: " + username);
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("tokenVersion", tokenVersion)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    private boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("Token has expired");
        } catch (JwtException e) {
            throw new InvalidTokenException("Token is invalid", e);
        }
    }

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

    public List<String> extractAuthorities(String token) {
        logger.info("Extracting authorities from token");
        Claims claims = extractAllClaims(token);
        return claims.get("authorities", List.class);
    }

    private boolean isTokenExpired(String token) {
        logger.info("Checking if token is expired");
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        logger.info("Extracting expiration date from token");
        return extractClaim(token, Claims::getExpiration);
    }

}
