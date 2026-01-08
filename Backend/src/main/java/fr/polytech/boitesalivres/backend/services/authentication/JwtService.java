package fr.polytech.boitesalivres.backend.services.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import fr.polytech.boitesalivres.backend.configurations.AuthConfiguration;
import fr.polytech.boitesalivres.backend.entities.User;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final AuthConfiguration configurationProperties;
    private final SecretKey secretKey;

    public JwtService(AuthConfiguration configurationProperties) {
        this.configurationProperties = configurationProperties;
        secretKey = Keys.hmacShaKeyFor(configurationProperties.getSecret().getBytes());
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + configurationProperties.getExpiration()))
                .claim("email", user.getEmail())
                .claim("username", user.getUsername())
                .signWith(secretKey)
                .compact();
    }

    public User getUserFromToken(String token) {
        Jws<Claims> jwt = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        Claims payload = jwt.getPayload();

        return User.builder()
                .id(Long.parseLong(payload.getSubject()))
                .email(payload.get("email", String.class))
                .username(payload.get("username", String.class))
                .build();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }


}