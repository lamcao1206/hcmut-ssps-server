package com.lamcao1206.hcmut_ssps.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${jwt.access.key}")
    private String jwtSecret;
    
    @Value("${jwt.access.expiration}")
    private String jwtExpirationMs;
    
    public String getAccessTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateToken(CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        String roles = userDetails.getAuthorities().stream().
                map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        Instant now = Instant.now();
        Instant expiration = now.plus(Long.parseLong(jwtExpirationMs), ChronoUnit.MILLIS);
        
        return Jwts.builder()
                .subject(userDetails.getId().toString())
                .claim("roles", roles)
                .claim("email", userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(key()).
                compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean validateToken(String authToken) throws JwtException {
        Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(authToken);
        return true;
    }
    
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
