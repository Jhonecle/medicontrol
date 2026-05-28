package medicontrol.security;

// Entidade do usuário do sistema (vem do banco)
import medicontrol.user.User;

// Repositório responsável por acessar o banco de dados
import medicontrol.repository.UserRepository;

// Interface padrão do Spring Security para carregar usuários
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

// Exceção usada quando o usuário não é encontrado
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// Marca a classe como um componente gerenciado pelo Spring
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por integrar o sistema de usuários
 * com o Spring Security.
 *
 * Ele busca o usuário no banco de dados e transforma em
 * um objeto compreendido pelo Spring Security (UserDetails).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Repositório de acesso ao banco de dados de usuários
    private final UserRepository userRepository;

    /**
     * Injeção de dependência via construtor
     * (boa prática no Spring)
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Método obrigatório do Spring Security.
     *
     * Ele é chamado automaticamente durante o processo de login
     * para buscar o usuário no banco de dados.
     */
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // Busca o usuário pelo email no banco de dados
        User user = userRepository.findByEmail(email)

                // Caso não encontre, lança exceção de autenticação
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuário não encontrado: " + email
                        )
                );

        // Converte a entidade User para o formato esperado pelo Spring Security
        return org.springframework.security.core.userdetails.User

                // Define o username (no caso, email)
                .withUsername(user.getEmail())

                // Define a senha (já deve estar criptografada com BCrypt)
                .password(user.getPassword())

                // Define as permissões do usuário (ROLE simples por enquanto)
                .authorities("ROLE_" + user.getRole())

                // Constrói o objeto final usado pelo Spring Security
                .build();
    }
}
