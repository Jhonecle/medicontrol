// Define o pacote onde este DTO está localizado
package medicontrol.dto.auth;

/**
 * DTO responsável por receber os dados
 * enviados no login do usuário.
 *
 * DTO = Data Transfer Object
 *
 * Ele serve para transportar dados entre
 * cliente (frontend/Postman) e backend.
 */
public class LoginRequestDTO {

    // =====================================
    // EMAIL DO USUÁRIO
    // =====================================

    // Email usado para autenticação
    private String email;

    // =====================================
    // SENHA DO USUÁRIO
    // =====================================

    // Senha enviada no login
    private String password;

    // =====================================
    // GETTERS E SETTERS
    // =====================================

    /**
     * Retorna o email informado
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email recebido
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna a senha informada
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha recebida
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
