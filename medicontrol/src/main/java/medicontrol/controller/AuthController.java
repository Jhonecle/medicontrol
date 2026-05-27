// Define pacote
package medicontrol.controller;

// Importa DTOs
import medicontrol.dto.AuthRequestDTO;
import medicontrol.dto.AuthResponseDTO;
import medicontrol.dto.RegisterDTO;

// Importa entidade User
import medicontrol.entity.User;

// Importa Repository
import medicontrol.repository.UserRepository;

// Importa JwtService
import medicontrol.security.JwtService;

// Importa validações
import jakarta.validation.Valid;

// Importa Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

// Importa Optional
import java.util.Optional;

// Define controller REST
@RestController

// Define rota principal
@RequestMapping("/auth")
public class AuthController {

    // =========================================
    // INJEÇÕES
    // =========================================

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtService jwtService;

    // Criptografia de senha
    private BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    // =========================================
    // REGISTER
    // =========================================

    // Endpoint:
    // POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(

            @Valid @RequestBody RegisterDTO dto) {

        // Verifica se email já existe
        Optional<User> usuarioExistente =
                repository.findByEmail(dto.getEmail());

        // Se existir
        if (usuarioExistente.isPresent()) {

            return ResponseEntity.badRequest()
                    .body("Email já cadastrado");
        }

        // Cria usuário
        User user = new User();

        // Copia dados
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());

        // Criptografa senha
        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );

        // Define perfil
        user.setRole(dto.getRole());

        // Salva usuário
        repository.save(user);

        // Gera token JWT
        String token =
                jwtService.generateToken(user.getEmail());

        // Retorna token
        return ResponseEntity.ok(
                new AuthResponseDTO(token)
        );
    }

    // =========================================
    // LOGIN
    // =========================================

    // Endpoint:
    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(

            @RequestBody AuthRequestDTO dto) {

        // Busca usuário pelo email
        Optional<User> userOptional =
                repository.findByEmail(dto.getEmail());

        // Verifica se usuário existe
        if (userOptional.isEmpty()) {

            return ResponseEntity.badRequest()
                    .body("Usuário não encontrado");
        }

        // Obtém usuário
        User user = userOptional.get();

        // Verifica senha
        boolean senhaCorreta =
                passwordEncoder.matches(
                        dto.getPassword(),
                        user.getPassword()
                );

        // Se senha inválida
        if (!senhaCorreta) {

            return ResponseEntity.badRequest()
                    .body("Senha inválida");
        }

        // Gera token JWT
        String token =
                jwtService.generateToken(user.getEmail());

        // Retorna token
        return ResponseEntity.ok(
                new AuthResponseDTO(token)
        );
    }
}
