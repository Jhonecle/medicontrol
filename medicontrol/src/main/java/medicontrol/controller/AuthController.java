package medicontrol.controller;

import medicontrol.dto.auth.AuthResponseDTO;
import medicontrol.dto.auth.RegisterRequestDTO;
import medicontrol.entity.User;
import medicontrol.repository.UserRepository;
import medicontrol.security.JwtService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Endpoint responsável pelo cadastro de novos usuários.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequestDTO dto) {

        Optional<User> usuarioExistente =
                repository.findByEmail(dto.getEmail());

        if (usuarioExistente.isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Email já cadastrado");
        }

        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());

        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );

        user.setRole("USER");

        repository.save(user);

        String token =
                jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(
                new AuthResponseDTO(token)
        );
    }
}
