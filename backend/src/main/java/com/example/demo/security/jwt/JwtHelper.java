package com.example.demo.security.jwt;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.example.demo.exceptions.AccessDeniedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    private static final int MINUTES = 120;

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String fullName){
        var now = Instant.now();
        return Jwts.builder()
            .setSubject(email)
            .claim("fullName", fullName)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
            .signWith(getSignKey())
            .compact();
    }

    public String extractUsername(String token){
        return getTokenBody(token).getSubject();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims getTokenBody(String token){
          try{
            return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch(io.jsonwebtoken.security.SignatureException | ExpiredJwtException e){
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        }
    }

    private boolean isTokenExpired(String token){
        Claims claims = getTokenBody(token);
        return claims.getExpiration().before(new Date());
    }

}
