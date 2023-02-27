package com.dev.ep02joaquinmoreno.configuration;


import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtProvider {


    private final byte[] signinKey;
    private final String SECRET_KEY = "cf9e71a7d3f66cf7bd3905a4830190239f82b1eb0fb14cf6b4d9396535a2c73e";
    private final long tokenValidityInSeconds;


    public JwtProvider(){
        this.signinKey = Decoders.BASE64.decode(SECRET_KEY);
        this.tokenValidityInSeconds=180;
    }


    public String generateTokenCambio(String from,String to){

        Claims claims = new DefaultClaims();
        //claims.setSubject(from);
        claims.put("from",from);
        claims.put("to",to);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+tokenValidityInSeconds*1000))
                .signWith(Keys.hmacShaKeyFor(signinKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateTokenValor(String from,String to, Float amount){

        Claims claims = new DefaultClaims();
        //claims.setSubject(from);
        claims.put("from",from);
        claims.put("to",to);
        claims.put("amount",amount);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+tokenValidityInSeconds*500))
                .signWith(Keys.hmacShaKeyFor(signinKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public Map<String,String> getClaimsFromToken(String token){
        Jws<Claims> claimsJws= Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);

        Map<String,String> currency = new HashMap<>();

        String  Currency1 =  claimsJws.getBody().get("from").toString();
        String  Currency2 =  claimsJws.getBody().get("to").toString();



        currency.put("from",Currency1);
        currency.put("to",Currency2);

        if( claimsJws.getBody().get("amount") !=null) {
            String amount = claimsJws.getBody().get("amount").toString();
            currency.put("amount", amount);
        }

        return currency;


    }

}


