package premium_pipe.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import premium_pipe.entity.AdminEntity;
import premium_pipe.model.response.JwtResponse;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${application.security.jwt.accessTokenTime}")
    private long accessTokenTime;
    @Value("${application.security.jwt.refreshTokenTime}")
    private long refreshTokenTime;
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateAccessToken(String username, String role) {
        return generateToken(username, role, accessTokenTime);
    }

    public String generateRefreshToken(String username, String role) {
        return generateToken(username, role, refreshTokenTime);
    }

    private String generateToken(String username, String role, Long expireTime) {
        return Jwts
                .builder()
                .claim("role", role)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSignInKey())
                .compact();
    }

    public JwtResponse decode(String token) {
        try {
            Claims claims = Jwts
                    .parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String username = claims.getSubject();
            String role = claims.containsKey("role") ? (String) claims.get("role") : null;
            return new JwtResponse(username, role);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }


    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isValidRefreshToken(String token, AdminEntity admin) {
        String username = decode(token).username();
        return username.equals(admin.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }


    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }

}
