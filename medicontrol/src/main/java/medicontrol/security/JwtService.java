// Define pacote
package medicontrol.security;

// Importa JWT
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// Importa chave de segurança
import io.jsonwebtoken.security.Keys;

// Importa Spring
import org.springframework.stereotype.Service;

// Importa criptografia
import java.security.Key;

// Importa datas
import java.util.Date;

// Classe de serviço JWT
@Service
public class JwtService {

    // =========================================
    // CHAVE SECRETA
    // =========================================

    // Chave usada para assinar token
    private static final String SECRET_KEY =
            "medicontroljwtsegredomuitoseguro123456789";

    // =========================================
    // GERA CHAVE CRIPTOGRÁFICA
    // =========================================

    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );
    }

    // =========================================
    // GERAR TOKEN
    // =========================================

    public String generateToken(String email) {

        // Cria token JWT
        return Jwts.builder()

                // Define usuário
                .setSubject(email)

                // Data de criação
                .setIssuedAt(new Date())

                // Expiração:
                // 1 dia
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60 * 24
                        )
                )

                // Assina token
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )

                // Gera token
                .compact();
    }

    // =========================================
    // EXTRAIR EMAIL
    // =========================================

    public String extractEmail(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    // =========================================
    // VALIDAR TOKEN
    // =========================================

    public boolean isTokenValid(String token, String email) {

        // Extrai email do token
        String extractedEmail = extractEmail(token);

        // Verifica validade
        return extractedEmail.equals(email)
                && !isTokenExpired(token);
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
    // EXTRAIR CLAIMS
    // =========================================

    private Claims extractAllClaims(String token) {

    return Jwts.parser()

            .setSigningKey(getSigningKey())

            .build()

            .parseClaimsJws(token)

            .getBody();
}
}