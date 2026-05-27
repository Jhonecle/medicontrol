// Define pacote
package medicontrol.controller;

// =========================================
// IMPORTA DTOs
// =========================================

// DTO de login
import medicontrol.dto.AuthRequestDTO;

// DTO de resposta com token JWT
import medicontrol.dto.AuthResponseDTO;

// DTO de cadastro
import medicontrol.dto.RegisterDTO;

// =========================================
// IMPORTA ENTIDADE
// =========================================

// Entidade User do banco de dados
import medicontrol.entity.User;

// =========================================
// IMPORTA REPOSITORY
// =========================================

// Repository responsável por acessar usuários
import medicontrol.repository.UserRepository;

// =========================================
// IMPORTA SECURITY
// =========================================

// Serviço responsável por gerar e validar JWT
import medicontrol.security.JwtService;

// =========================================
// IMPORTA VALIDAÇÕES
// =========================================

// Validação automática dos DTOs
import jakarta.validation.Valid;

// =========================================
// IMPORTA SPRING
// =========================================

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

// =========================================
// IMPORTA OPTIONAL
// =========================================

import java.util.Optional;

// =========================================
// DEFINE COMO CONTROLLER REST
// =========================================

@RestController

// Rota principal:
// /auth
@RequestMapping("/auth")
public class AuthController {

    // =========================================
    // INJEÇÕES DE DEPENDÊNCIA
    // =========================================

    // Repository de usuários
    @Autowired
    private UserRepository repository;

    // Serviço JWT
    @Autowired
    private JwtService jwtService;

    // Encoder de senha BCrypt
    @Autowired
    private PasswordEncoder passwordEncoder;

    // =========================================
    // REGISTER
    // =========================================

    /**
     * Endpoint responsável pelo cadastro
     * de novos usuários.
     *
     * POST /auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(

            // Recebe JSON do body
            @Valid @RequestBody RegisterDTO dto) {

        // =========================================
        // VERIFICA SE EMAIL JÁ EXISTE
        // =========================================

        Optional<User> usuarioExistente =
                repository.findByEmail(dto.getEmail());

        // Se já existir
        if (usuarioExistente.isPresent()) {

            return ResponseEntity.badRequest()
                    .body("Email já cadastrado");
        }

        // =========================================
        // CRIA NOVO USUÁRIO
        // =========================================

        User user = new User();

        // Define nome
        user.setNome(dto.getNome());

        // Define email
        user.setEmail(dto.getEmail());

        // =========================================
        // CRIPTOGRAFA SENHA
        // =========================================

        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );

        // =========================================
        // DEFINE PERFIL
        // =========================================

        // Caso role venha null
        if (dto.getRole() == null || dto.getRole().isBlank()) {

            user.setRole("USER");

        } else {

            user.setRole(dto.getRole());
        }

        // =========================================
        // SALVA NO BANCO
        // =========================================

        repository.save(user);

        // =========================================
        // GERA TOKEN JWT
        // =========================================

        String token =
                jwtService.generateToken(user.getEmail());

        // =========================================
        // RETORNA TOKEN
        // =========================================

        return ResponseEntity.ok(
                new AuthResponseDTO(token)
        );
    }

    // =========================================
    // LOGIN
    // =========================================

    /**
     * Endpoint responsável pelo login.
     *
     * POST /auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(

            // Recebe JSON do body
            @RequestBody AuthRequestDTO dto) {

        // =========================================
        // BUSCA USUÁRIO PELO EMAIL
        // =========================================

        Optional<User> userOptional =
                repository.findByEmail(dto.getEmail());

        // =========================================
        // VERIFICA SE USUÁRIO EXISTE
        // =========================================

        if (userOptional.isEmpty()) {

            return ResponseEntity.badRequest()
                    .body("Usuário não encontrado");
        }

        // Obtém usuário
        User user = userOptional.get();

        // =========================================
        // VALIDA SENHA
        // =========================================

        boolean senhaCorreta =
                passwordEncoder.matches(
                        dto.getPassword(),
                        user.getPassword()
                );

        // =========================================
        // SENHA INVÁLIDA
        // =========================================

        if (!senhaCorreta) {

            return ResponseEntity.badRequest()
                    .body("Senha inválida");
        }

        // =========================================
        // GERA TOKEN JWT
        // =========================================

        String token =
                jwtService.generateToken(user.getEmail());

        // =========================================
        // RETORNA TOKEN
        // =========================================

        return ResponseEntity.ok(
                new AuthResponseDTO(token)
        );
    }
}
