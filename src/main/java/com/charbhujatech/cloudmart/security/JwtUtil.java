package com.charbhujatech.cloudmart.security;

import com.charbhujatech.cloudmart.util.ConstantsConfig;
import com.charbhujatech.cloudmart.util.ConstantsString;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private String SECRET = ConstantsConfig.JWT_SECRET_CODE;
    private SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long EXPIRATION_TIME = ConstantsConfig.JWT_TOKEN_EXPIRATION_TIME;

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities)
    {
        String compact = Jwts.builder()
                .setIssuer(ConstantsString.JWT_ISSUER)
                .setSubject(ConstantsString.JWT_SUBJECT)
                .setIssuedAt(new Date())
                .claim(ConstantsString.JWT_USERNAME,username)
                .claim(ConstantsString.JWT_AUTHORITIES,authorities.stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return compact;
    }

    public String extractUsername(String token) {
        return String.valueOf(extractClaim(token).get(ConstantsString.JWT_USERNAME));
    }

    private Claims extractClaim(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public boolean validateToken(String username, UserDetails userDetails, String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token).getExpiration().before(new Date());
    }
}
