// Define pacote
package medicontrol.dto;

// Classe DTO para cadastro
public class RegisterDTO {

    // Nome do usuário
    private String nome;

    // Email do usuário
    private String email;

    // Senha do usuário
    private String senha;

    // Perfil do usuário
    private String role;

    // =========================
    // GETTERS E SETTERS
    // =========================

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

    public String getPassword() {
        return senha;
    }

    public void setPassword(String senha) {
        this.senha = senha;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
