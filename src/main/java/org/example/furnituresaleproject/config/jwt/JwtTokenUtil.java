package org.example.furnituresaleproject.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_TIME = 300000; // limited 5m

    @Value("${jwt.secret}")
    private String secret;

//    private Claims getAll

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).getBody();
        return claimResolver.apply(claims);
    }

    // retrieve id account
    public Long getUserId( String token) {
        return Long.parseLong( Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).getBody().getSubject());
    }
    //retrieve email from jwt token
    public String getEmailFromToken(String token) {return getClaimFromToken(token, Claims::getSubject);}

//    get expireTime from token
    public Date getExpireTImeFromToken(String token) { return getClaimFromToken(token, Claims::getExpiration);}

    // check  if time has expired
    private Boolean isExpiredTime(String token) {
        final Date expiration = getExpireTImeFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // while create token
    // define claims of token such as: IssuerAt, expiration, ....
    // gen Sign from password and secret by Algorithm Hs512
    // compact is to convert url-safe string
    public String doGenerateToken(Map<String, Object> claims, String subject) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now+System.currentTimeMillis() + EXPIRE_TIME*1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isExpiredTime(token));
    }

//    public boolean isValid(String token) {
//        try {
//            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

}
