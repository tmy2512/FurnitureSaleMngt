package org.example.furnituresaleproject.service.AccountService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Collections;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

public class JWTTokenService {

    private static final long EXPIRATION_TIME = 864000000;
    private static final String SECRET = "123456";
    private static final String PREFIX_TOKEN = "Bearer";
    private static final String AUTHORIZATION = "Authorization";


    public static SecretKey getSigningKey() {
        byte[] keyBytes = SECRET.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }
    public static void addJWTTokenToHeader(HttpServletResponse response, String email) {
        String JWT = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
        response.addHeader(AUTHORIZATION,  PREFIX_TOKEN+" "+JWT);
    }

public static Authentication parseTokenToUserInformation(HttpServletRequest request) {
    String token = request.getHeader(AUTHORIZATION);
    if (token == null) {
        return null;
    }

    // parse token
    Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token.replace(PREFIX_TOKEN, ""))
            .getBody();
    String email = claims.getSubject();
    return email != null?
            new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList()):
            null;
}
}
