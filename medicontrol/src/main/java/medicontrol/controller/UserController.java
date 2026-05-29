package medicontrol.controller;

// =========================================
// IMPORTS DA ENTIDADE E REPOSITÓRIO
// =========================================
import medicontrol.repository.UserRepository;

// =========================================
// IMPORTS DO SPRING
// =========================================
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * =========================================
 * CONTROLLER DE USUÁRIO
 * =========================================
 *
 * Gerencia endpoints que exigem autenticação.
 * Todas as rotas aqui exigem token JWT válido.
 *
 * Authorization: Bearer <token>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    // Acesso ao banco de dados de usuários
    @Autowired
    private UserRepository repository;

    /**
     * =========================================
     * ENDPOINT: GET /user/me
     * =========================================
     *
     * Retorna os dados do usuário autenticado.
     *
     * Fluxo:
     * 1. JWT Filter extrai o email do token
     * 2. Spring injeta o Authentication
     * 3. Buscamos o usuário no banco pelo email
     * 4. Retornamos nome, email e role
     *
     * Exige: Authorization: Bearer <token>
     */
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

        // Extrai o email do token JWT autenticado
        String email = authentication.getName();

        // Busca no banco e retorna os dados
        return repository.findByEmail(email)
                .map(user -> ResponseEntity.ok(
                        new MeResponseDTO(
                                user.getNome(),
                                user.getEmail(),
                                user.getRole().name()
                        )
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================================
    // DTO DE RESPOSTA
    // =========================================
    // Transporta apenas os dados necessários.
    // Nunca expõe a senha do usuário.
    record MeResponseDTO(
            String nome,
            String email,
            String role
    ) {}
}
