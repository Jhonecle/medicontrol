// Define pacote
package medicontrol.repository;

// Importa entidade User
import medicontrol.user.User;

// Importa JPA Repository
import org.springframework.data.jpa.repository.JpaRepository;

// Importa Optional
import java.util.Optional;

// Repository de usuários
public interface UserRepository
        extends JpaRepository<User, Long> {

    // Busca usuário pelo email
    Optional<User> findByEmail(String email);
}
