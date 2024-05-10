package com.testsecurity.test.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static  final String SECRET_KEY = "160e392cb60a6caa665c07067a00582eb993516de6ef84b8d69697d532805bd0";

    public String extractUsername(String token) {
        return extractClaim(token , Claims::getSubject); // the subject of the token should be the email of the user
    }

    // To extract a single claim that we pass
    // T : to be a generic method
    //Claims : type of function and T is the return type
    public <T> T extractClaim(String token , Function<Claims, T > claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    //to generate the token
    //the map will contain the claims or the extra claims that we want to add
    //IssuedAt (when this claim was created to calculate the expiration date and to check if it's still valid or not 24hours + 1000 milliseconds )
    //compact is to generate and return the token
    public String generateToken(Map<String , Object> extraClaims , UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact() ; 
    }

    //when we try to generate or to decode a token we need to use the signinKey
    //Parse our token and once the token is parsed we can call the method get Body -> get body( we can get all the claims that we have within the token
    private Claims extractAllClaims (String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSigninKey() {
        //Decode the secret Key
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
