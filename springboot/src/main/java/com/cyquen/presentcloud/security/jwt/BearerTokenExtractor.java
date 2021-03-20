package com.cyquen.presentcloud.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Payload;
import com.cyquen.presentcloud.entity.Role;
import com.cyquen.presentcloud.security.CurrentUserDetails;
import com.cyquen.presentcloud.service.UserDetailsServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class BearerTokenExtractor implements TokenExtractor {

    private final static Log logger = LogFactory.getLog(BearerTokenExtractor.class);

    public static final String AUTHORIZATION_HEADER_KEY = "Authorization";

    UserDetailsServiceImpl userService;

    public BearerTokenExtractor(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public Authentication extract(HttpServletRequest request) {
        String tokenValue = extractHeaderToken(request);
        if (tokenValue == null) {
            return null;
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("cyquen2021")).build();
        Payload payload;
        try {
            payload = jwtVerifier.verify(tokenValue);
        } catch (JWTVerificationException e) {
            logger.info(e.getMessage());
            return null;
        }
        Integer userId = Integer.parseInt(payload.getAudience().get(0));
        String username = payload.getClaims().get("username").asString();
        List<Role> roles = userService.getRolesByUserId(userId);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(roles.size());
        CurrentUserDetails u = new CurrentUserDetails(userId, username, roles);

        return new UsernamePasswordAuthenticationToken(u, null, authorities);
    }

    protected String extractHeaderToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_KEY);
        if (StringUtils.hasText(authorizationHeader) &&
                authorizationHeader.startsWith(TokenService.AUTHORIZATION_HEADER_PREFIX)) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
