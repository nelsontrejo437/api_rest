package org.seguridad.api_rest.utils;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
private String secret_key = "hjasdwqlkjuasd";

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public java.util.Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(0));
    }

    public Boolean validateToken(String token, UserDetails details) {
        String username = extractUsername(token);
        return (username.equals(details.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validatedTokens(String token) {
        String username = extractUsername(token);
        return username!= null && !isTokenExpired(token);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) // fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // expiración -> 1 dia
                .signWith(SignatureAlgorithm.HS256, secret_key) // firma con HS256 y clave secreta
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody();
    }
}
