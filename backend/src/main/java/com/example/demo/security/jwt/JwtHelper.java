package com.example.demo.security.jwt;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.exceptions.AccessDeniedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtHelper {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final int MINUTES = 120;

    public static String generateToken(String email, String fullName){
        var now = Instant.now();
        return Jwts.builder()
            .setSubject(email)
            .claim("fullName", fullName)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
            .signWith(SECRET_KEY)
            .compact();
    }

    public static String extractUsername(String token){
        return getTokenBody(token).getSubject();
    }

    public static Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private static Claims getTokenBody(String token){
          try{
            return Jwts
                .parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch(io.jsonwebtoken.security.SignatureException | ExpiredJwtException e){
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        }
    }

    private static boolean isTokenExpired(String token){
        Claims claims = getTokenBody(token);
        return claims.getExpiration().before(new Date());
    }

}
