package cl.duoc.backend_adopcion.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Leemos la clave secreta desde application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    // Leemos la expiración (24 horas)
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // --- 1. Generar Token ---
    public String generateToken(String username) {
        return buildToken(new HashMap<>(), username, jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, String subject, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(subject) // El usuario (email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Fecha de vencimiento
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Firma digital
                .compact();
    }

    // --- 2. Validar Token ---
    public boolean isTokenValid(String token, String username) {
        final String usernameFromToken = extractUsername(token);
        return (usernameFromToken.equals(username)) && !isTokenExpired(token);
    }

    // --- 3. Extraer datos del Token ---
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}