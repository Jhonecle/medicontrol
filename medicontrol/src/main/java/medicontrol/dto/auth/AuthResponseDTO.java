// Define pacote
package medicontrol.dto.auth;

// DTO de resposta JWT
public class AuthResponseDTO {

    // Token JWT
    private String token;

    // Construtor
    public AuthResponseDTO(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Setter
    public void setToken(String token) {
        this.token = token;
    }
}
