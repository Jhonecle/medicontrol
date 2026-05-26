// Define o pacote da classe
package medicontrol.security;

// Importa configuração do Spring
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Importa configuração HTTP
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// Importa filtro de segurança
import org.springframework.security.web.SecurityFilterChain;

// Classe de configuração
@Configuration
public class SecurityConfig {

    // Método responsável pelas regras de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        // Desabilita CSRF
        // APIs REST normalmente utilizam JWT
        http.csrf(csrf -> csrf.disable())

                // Configura permissões das rotas
                .authorizeHttpRequests(auth -> auth

                        // Libera Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Libera endpoints de medicamentos
                        .requestMatchers("/medicamentos/**")
                        .permitAll()

                        // Qualquer outra rota precisa autenticação
                        .anyRequest()
                        .authenticated()
                );

        // Retorna configuração pronta
        return http.build();
    }
}
