package com.app.Utils;

import com.app.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil
{
    private  String tokenSecret = "4hbr3298f892p[#$)#$mkfjw9u3@refkl30?343mg9faolmf";

    private String generateToken(String email, Map<String, Object> claims ,Long expireInMillis)
    {
        return Jwts.builder().claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireInMillis))
                .signWith(getSigningKey())
                .subject(email)
                .compact();
    }

    private SecretKey getSigningKey()
    {
        return Keys.hmacShaKeyFor(tokenSecret.getBytes());
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    public String generateSignUpToken(@NotBlank String email)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", "user");
        claims.put("scope", "verifyEmail");
         return generateToken(email,claims,24 * 60 * 60 * 1000L);
    }

    public int validateSignUpToken(String signUpToken)
    {

        if(validateTokenExists(signUpToken)){
            final Claims claimsFromToken = getClaimsFromToken(signUpToken);
            if(claimsFromToken.get("scope").toString().equalsIgnoreCase("verifyEmail"))
                return 1;
            return -1;
        }
        else
            return 0;

    }

    public boolean validateTokenExists(String token)
    {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String signUpToken)
    {
       return getClaimsFromToken(signUpToken).getExpiration().before(new Date());
    }

    public String getSubjectFromToken(String signUpToken)
    {
        final Claims claimsFromToken = getClaimsFromToken(signUpToken);
        return claimsFromToken.getSubject();
    }

    public String generateLoginToken(User dbUser)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", dbUser.getEmail());
        claims.put("role", "user");
        claims.put("scope", "login");
        return generateToken(dbUser.getEmail(), claims,2 * 60 * 60 * 1000L);

    }
}
