package com.security.basic.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.expire.time}")
    private Long expireTime;
    private final KeyPair keyPair = generateKeyPair();

    public String generateToken(String username) {
        PrivateKey privateKey = keyPair.getPrivate();

        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .issuer("http://localhost:8080")
                .audience().add("http://localhost:8080").and()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime ))
                .claims(claims)
                .signWith(privateKey, Jwts.SIG.RS512)
                .compact();
    }


    public boolean validateToken(String token, UserDetails userDetails) {

        var user = extractClaim(token, Claims::getSubject);
        var expirationDate = extractClaim(token, Claims::getExpiration);
        return user.equals(userDetails.getUsername()) && expirationDate.after(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> function) {

        PublicKey publicKey = keyPair.getPublic();

        Claims payload = Jwts.parser().verifyWith(publicKey)
                .build().parseSignedClaims(token).getPayload();
        return function.apply(payload);

    }

    private static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //    SecretKey usage *************

    public String generateTokenSecretKey(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .issuer("http://localhost:8080")
                .audience().add("http://localhost:8080").and()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 25))
                .claims(claims)
                .signWith(getSecretKey())
                .compact();
    }

    public <T> T extractClaimSecret(String token, Function<Claims, T> function) {
        Claims payload = Jwts.parser().
                verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
        return function.apply(payload);
    }

    private SecretKey getSecretKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

}
