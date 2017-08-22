package com.spittr.manager;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Configuration
public class TokenManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static final long EXPIRATIONTIME = 1000*60*60*24*1; // 1天
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String AUTHORIZATION = "Authorization";

    @Autowired
    private RedisTemplate<String, String> redis;

    public void addToken(String username, HttpServletResponse response) {
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        jwt = TOKEN_PREFIX + " " + jwt;
        response.addHeader(AUTHORIZATION,  jwt);
        redis.boundValueOps(username).set(jwt, EXPIRATIONTIME, TimeUnit.MILLISECONDS);
    }

    public boolean validate(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(AUTHORIZATION);
        if (token == null) {
            return false;
        }
        String username = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, "").trim())
                .getBody()
                .getSubject();
        logger.info("validate auth with token: " + token + " , parsered username is " + username + ".");
        token = redis.boundValueOps(username).get();
        if (token == null) {
            return false;
        }
        if (username.length() > 0) {
            return true;
        } else {
            response.setHeader(AUTHORIZATION, null);
            return false;
        }
    }

    public void invalidate(String username, HttpServletResponse response) {
        redis.delete(username);
        response.setHeader(AUTHORIZATION, null);
    }
}