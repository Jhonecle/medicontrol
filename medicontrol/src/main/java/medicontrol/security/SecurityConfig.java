// Define pacote
package medicontrol.security;

// Importa configuração Spring
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Importa BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Importa segurança HTTP
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// Importa filtro de segurança
import org.springframework.security.web.SecurityFilterChain;

// Classe de configuração
@Configuration
public class SecurityConfig {

    // =========================================
    // PASSWORD ENCODER
    // =========================================

    // Bean de criptografia BCrypt
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // =========================================
    // CONFIGURAÇÃO DE SEGURANÇA
    // =========================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        // Desabilita CSRF
        http.csrf(csrf -> csrf.disable())

                // Configura permissões
                .authorizeHttpRequests(auth -> auth

                        // Libera autenticação
                        .requestMatchers("/auth/**")
                        .permitAll()

                        // Libera Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Libera medicamentos
                        .requestMatchers("/medicamentos/**")
                        .permitAll()

                        // Qualquer outra rota
                        // exige autenticação
                        .anyRequest()
                        .authenticated()
                );

        // Retorna configuração
        return http.build();
    }
}
