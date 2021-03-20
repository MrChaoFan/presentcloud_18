package com.cyquen.presentcloud.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cyquen.presentcloud.security.CurrentUserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TokenService {

    public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    public AccessToken createToken(CurrentUserDetails user, int during) {
        Date createdTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, during);
        Date expirationTime = calendar.getTime();

        String token = JWT.create()
                .withAudience(Integer.toString(user.getId()))
                .withClaim("username", user.getUsername())
                .withExpiresAt(expirationTime)
                .withIssuedAt(createdTime)
                .sign(Algorithm.HMAC256("cyquen2021"));
        return new AccessToken(AUTHORIZATION_HEADER_PREFIX + token, expirationTime);
    }
}
