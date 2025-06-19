package com.job_portal.job_portal.service;

import com.job_portal.job_portal.enums.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private Boolean isTokenExpired(String token){
        Date expiration=extractClaim(token,Claims::getExpiration);
        return expiration.before(new Date());
    }
    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public String extractRole(String token){
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
    private Boolean isTokenValid(String token, UserDetails userDetails){
        String username=extractClaim(token,Claims::getSubject);
        return (!Objects.equals(username, userDetails.getUsername()) && !isTokenExpired(token));
    }
    public String generateToken(String email, String userName, Roles role){
        Map<String,Object> claims=new HashMap<>();
        claims.put("email",email);
        //claims.put("username",userName);
        claims.put("role", role);
        return createToken(claims,userName);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String createToken(Map<String, Object> claims, String subject){
        long expirationTime = 1000 * 60 * 60;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setSubject(subject)
                .signWith(secretKey())
                .compact();
    }
    private Key secretKey(){
        byte [] key= Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(key);
    }
}
