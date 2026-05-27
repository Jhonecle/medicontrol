// Define pacote
package medicontrol.dto;

// Classe DTO para login
public class AuthRequestDTO {

    // Email do usuário
    private String email;

    // Senha do usuário
    private String senha;

    // =========================
    // GETTERS E SETTERS
    // =========================

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
}
