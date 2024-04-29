package com.origin.library.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.origin.library.infrastructure.utils.RandomUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${jwt.sign}")
    private String sign;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    private String salt;

    private static final String separator = "\n";

    JwtService() {
        this.salt = RandomUtils.generateRandomString(256);
    }

    private String encryptedSubject(String id) throws Exception {
        String subject = id + separator + salt;
        AESEncryptionService aes = new AESEncryptionService(secret);
        String encryptedSubject = aes.encryptString(subject);
        if (encryptedSubject == null) {
            throw new Exception("encrypt subject in jwt");
        }
        return encryptedSubject;
    }

    private String decryptedSubject(String encryptedSubject) throws Exception {
        AESEncryptionService aes = new AESEncryptionService(secret);
        String subject = aes.decryptString(encryptedSubject);
        if (subject == null) {
            throw new Exception("decrypt subject in jwt");
        }
        String[] parts = subject.split(separator);
        if (parts.length != 2) {
            throw new Exception("invalid subject in jwt");
        }
        return parts[0];
    }

    public String generateToken(String id) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        String subject = encryptedSubject(id);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, sign)
                .compact();
    }

    public void injectToken(HttpHeaders headers, String token) {
        headers.set("Authorization", "Bearer " + token);
    }

    public String extractToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

    public String extractId(String token) throws Exception {
        String encryptedSubject = extractClaim(token, Claims::getSubject);
        return decryptedSubject(encryptedSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(sign).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractExpirationDate(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, String id) throws Exception {
        final String tokenId = extractId(token);
        return (tokenId.equals(id) && !isTokenExpired(token));
    }
}
