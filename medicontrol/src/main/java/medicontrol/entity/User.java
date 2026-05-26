// Define o pacote da classe
package medicontrol.entity;

// Importa JPA
import jakarta.persistence.*;

// Define esta classe como entidade do banco
@Entity

// Define nome da tabela
@Table(name = "usuarios")
public class User {

    // =========================
    // ID
    // =========================

    // Define chave primária
    @Id

    // Auto incremento
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // NOME
    // =========================

    // Nome do usuário
    @Column(nullable = false)
    private String nome;

    // =========================
    // EMAIL
    // =========================

    // Email único
    @Column(nullable = false, unique = true)
    private String email;

    // =========================
    // SENHA
    // =========================

    // Senha criptografada
    @Column(nullable = false)
    private String senha;

    // =========================
    // PERFIL
    // =========================

    // Exemplo:
    // ADMIN
    // USER
    @Column(nullable = false)
    private String role;

    // =========================
    // GETTERS E SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
