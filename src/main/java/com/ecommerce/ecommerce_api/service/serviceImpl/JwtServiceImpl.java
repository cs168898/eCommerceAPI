package com.ecommerce.ecommerce_api.service.serviceImpl;

import com.ecommerce.ecommerce_api.service.JwtService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    public static final String SECRET = "my-super-long-utf8-secret-key-1234567890";
    byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);

    private final Key signKey =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email) {
        String jwt = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1_800_000))
                .signWith(signKey, SignatureAlgorithm.HS256)
                .compact();
        System.out.println("### JWT ISSUED → " + jwt);
        return jwt;
    }

    // helper method to create the token
    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims) // include any extra data
                .setSubject(email) // set the email as the subject
                .setIssuedAt(new Date()) //set the issued date to the current date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 mins expiry
                .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        // decodes the secret string to a key to sign and verify JWTs
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        // get the subject which is set as the email
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        // get the expiration details of the token
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // helper function to get the fields from token claims
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token) {
        // check if the token expiration is before the current time and date
        // means the expiration date and time has been hit before the current date and time
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        // reads and prases the whole JWT to get the claims and data
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        // validates the username with the user trying to use it and
        // check the token if its expired or not
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}








