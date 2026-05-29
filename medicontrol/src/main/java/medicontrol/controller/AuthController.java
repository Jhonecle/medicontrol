package medicontrol.controller;

// =========================================
// IMPORTS DOS DTOs
// =========================================
import medicontrol.dto.auth.AuthResponseDTO;
import medicontrol.dto.auth.LoginRequestDTO;
import medicontrol.dto.auth.RegisterRequestDTO;

// =========================================
// IMPORTS DA ENTIDADE E REPOSITÓRIO
// =========================================
import medicontrol.user.Role;
import medicontrol.user.User;
import medicontrol.repository.UserRepository;

// =========================================
// IMPORT DO SERVIÇO JWT
// =========================================
import medicontrol.security.JwtService;

// =========================================
// IMPORTS DO JAKARTA E SPRING
// =========================================
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * =========================================
 * CONTROLLER DE AUTENTICAÇÃO
 * =========================================
 *
 * Gerencia os endpoints públicos de:
 * → POST /auth/register  (cadastro)
 * → POST /auth/login     (login)
 *
 * Não exige token JWT para acessar.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    // =========================================
    // INJEÇÃO DE DEPENDÊNCIAS
    // =========================================

    /** Acesso ao banco de dados de usuários */
    @Autowired
    private UserRepository repository;

    /** Geração e validação de tokens JWT */
    @Autowired
    private JwtService jwtService;

    /** Criptografia e comparação de senhas */
    @Autowired
    private PasswordEncoder passwordEncoder;

    // =========================================
    // ENDPOINT: REGISTER
    // =========================================

    /**
     * Cadastra um novo usuário.
     *
     * Fluxo:
     * 1. Verifica se email já existe
     * 2. Criptografa a senha
     * 3. Salva com role USER
     * 4. Retorna token JWT
     *
     * POST /auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequestDTO dto) {

        // Verifica duplicidade de email
        Optional<User> usuarioExistente =
                repository.findByEmail(dto.getEmail());

        if (usuarioExistente.isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Email já cadastrado");
        }

        // Cria e preenche o usuário
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());

        // Criptografa senha antes de salvar
        user.setSenha(passwordEncoder.encode(dto.getPassword()));

        // Role padrão para novos usuários
        user.setRole(Role.USER);

        // Persiste no banco
        repository.save(user);

        // Gera e retorna o token JWT
        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    // =========================================
    // ENDPOINT: LOGIN
    // =========================================

    /**
     * Autentica um usuário existente.
     *
     * Fluxo:
     * 1. Busca usuário pelo email
     * 2. Compara senha enviada com hash do banco
     * 3. Retorna token JWT se válido
     *
     * POST /auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDTO dto) {

        // Busca usuário pelo email
        Optional<User> usuarioOpt =
                repository.findByEmail(dto.getEmail());

        // Email não encontrado → 401
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401)
                    .body("Email não encontrado");
        }

        User user = usuarioOpt.get();

        // Compara senha enviada com hash salvo no banco
        // passwordEncoder.matches() faz isso com segurança
        if (!passwordEncoder.matches(
                dto.getPassword(), user.getSenha())) {
            return ResponseEntity.status(401)
                    .body("Senha incorreta");
        }

        // Credenciais válidas — gera token JWT
        String token = jwtService.generateToken(user.getEmail());

        // Retorna 200 OK com o token
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
