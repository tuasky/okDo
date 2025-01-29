package com.okdo.common.core.security;

import com.okdo.common.util.StringUtils;
import com.okdo.domain.User;
import com.okdo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    @Autowired
    private UserService userService;

    public String generateToken(String content) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(content)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        if (!isTokenExpired(token))
            return false;
        final String extractedContent = extractClaims(token).getSubject();
        User user = userService.getUserByUid(Long.valueOf(extractedContent));
        if (user == null)
            return false;
        HostHolder.set(user);
        return true;
    }

    private boolean isTokenExpired(String token) {
        if (StringUtils.isEmpty(token))
            return false;
        return extractClaims(token).getExpiration().before(new Date());
    }
}
