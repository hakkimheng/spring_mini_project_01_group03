package org.example.miniproject.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.miniproject.model.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    public static final long JWT_TOKEN_VALIDITY = 15 * 60 ; // 15mins
    public static final String SECRET = "FVPr6Q/fVlHGZkElZubC0Zaxv657dPUfDQ4o9DADjSin7+uST1d2A5klMWrMK8fmSl3doyf2wn5zj56VC+qqCg==";

    private String createToken(Map<String, Object> claim, String subject) {
        return Jwts.builder()
                .setClaims(claim)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSignKey()).compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //2. generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        AppUser appUser = (AppUser) userDetails;
        claims.put("user_id", appUser.getId());
        claims.put("user_role",appUser.getRole());
        return createToken(claims, appUser.getEmail());
    }

    //3. retrieving any information from token we will need the secret key
    private Claims extractAllClaim(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token is null or empty");
        }
        token = token.trim(); // Trim to remove any extra spaces
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // your secret key
                .build()
                .parseClaimsJws(token) // for signed JWT
                .getBody(); // returns the Claims
    }

    //4. extract a specific claim from the JWT token’s claims.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    //5. retrieve username from jwt token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //6. retrieve expiration date from jwt token
    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //7. check expired token
    private Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    //8. validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
