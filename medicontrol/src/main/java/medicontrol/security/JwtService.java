// Define pacote
package medicontrol.security;

// JWT imports
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// Spring
import org.springframework.stereotype.Service;

// Java
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // =========================================
    // CHAVE SECRETA
    // =========================================
    private static final String SECRET_KEY =
            "medicontroljwtsegredomuitoseguro123456789";

    // =========================================
    // GERAR CHAVE DE ASSINATURA (CORRIGIDO)
    // =========================================
    private SecretKey getSigningKey() {

        // Converte string em chave segura para HS256
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // =========================================
    // GERAR TOKEN JWT
    // =========================================
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email) // usuário do token (API nova)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(getSigningKey()) // versão nova não precisa SignatureAlgorithm
                .compact();
    }

    // =========================================
    // EXTRAIR EMAIL
    // =========================================
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // =========================================
    // VALIDAR TOKEN
    // =========================================
    public boolean isTokenValid(String token) {

        try {
            String email = extractEmail(token);

            return email != null && !isTokenExpired(token);

        } catch (Exception e) {
            return false;
        }
    }

    // =========================================
    // VERIFICAR EXPIRAÇÃO
    // =========================================
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // =========================================
    // EXTRAIR CLAIMS (JJWT 0.12.5 CORRETO)
    // =========================================
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}