package com.nisanth.springsecurity.services.impl;

import com.nisanth.springsecurity.entities.User;
import com.nisanth.springsecurity.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Map;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService
{
    @Override
    public String extractuserName(String token) {

        return extractClaim(token,Claims::getSubject);

    }

    @Override


    // First;y generate the JWT token
    public String generateToken(UserDetails userDetails)
    {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*24))
                .signWith(getSiginkey(), SignatureAlgorithm.HS256)
                .compact();
    }


   private <T> T extractClaim(String token, Function<Claims,T> claimsResolvers)
   {
       final Claims claims=extractAllClaims(token);
       return claimsResolvers.apply(claims);
   }
    private Key getSiginkey()
    {
        byte[] key= Decoders.BASE64.decode("413F4428472B4B62506553685660597033733676397924422645294840406351");
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder().setSigningKey(getSiginkey()).build().parseClaimsJws(token).getBody();
     }

     public boolean istokenValid(String token,UserDetails userDetails)
     {
         // get username from token
         final String username=extractuserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
     }


    public String generateRefreshToken(HashMap<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +604800000))// 7 days
                .signWith(getSiginkey(), SignatureAlgorithm.HS256)
                .compact();
    }





    private boolean isTokenExpired(String token)
     {
         return extractClaim(token,Claims::getExpiration).before(new Date());

     }
}
