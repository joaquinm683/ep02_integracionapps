package com.dev.ep02joaquinmoreno.exchange;

import com.dev.ep02joaquinmoreno.configuration.JwtException;
import com.dev.ep02joaquinmoreno.configuration.JwtProvider;
import com.dev.ep02joaquinmoreno.secrets.SecretService;
import com.dev.ep02joaquinmoreno.shared.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenService {


    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private SecretService secretService;


    public String generateTokenCambio(String from, String too) throws Exception {

      try {

            return jwtProvider.generateTokenCambio(from, too);


        }catch (Exception e){
            throw new Exception(Constants.ERROR_MESSAGE_WHEN_INCORRECT_REQUEST);
        }
    }

    public String generateTokenValor(String from, String too, Float amount) throws Exception {

        try {

            return jwtProvider.generateTokenValor(from, too,amount);


        }catch (Exception e){
            throw new Exception(Constants.ERROR_MESSAGE_WHEN_INCORRECT_REQUEST);

        }
    }

    public Map<String, String> getClaimsFromJWT(String token){
       try{
           return jwtProvider.getClaimsFromToken(token);
       }catch (ExpiredJwtException e){
           throw  new JwtException(Constants.ERROR_MESSAGE_WHEN_JWT_EXPIRED,Constants.HTTP_CODE_JWT_EXPIRED);
       }
    }












}
