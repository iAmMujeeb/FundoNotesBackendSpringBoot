package com.example.notes.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtToken {

    private final String SECRET = "Mujeeb";

    public String createToken(int id) {
        return JWT.create()
                .withClaim("id", id)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public int decodeToken(String token){
        int id = 0;
        if (token!=null){
            id = JWT.require(Algorithm.HMAC256(SECRET))
                    .build().verify(token)
                    .getClaim("id").asInt();
        }
        return id;
    }
}
