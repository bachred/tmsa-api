package com.example.tmsaapi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    public static final long TOKEN_VALIDITY = 7 * 60 * 60;

    @Value("${jwt.secret}")
    private String TOKEN_SECRET;

    public String generateToken(String subject) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("sub", subject);
        claims.put("created", new Date());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .compact();
    }

    public String getSubjectFromToken(String token) {

        try {

            Claims claims = getClaims(token);

            return claims.getSubject();

        } catch (Exception e) {
            return null;
        }
    }

    private Claims getClaims(String token) {

        try {
            Claims claims = Jwts.parser().setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            return claims;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenValid(String token, String username) {

        String subject = getSubjectFromToken(token);

        return (subject.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        try {

            Date expiration = getClaims(token).getExpiration();
            return expiration.before(new Date());

        } catch (Exception e) {
            return false;
        }

    }

}
