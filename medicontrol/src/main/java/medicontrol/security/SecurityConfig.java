// Define pacote
package medicontrol.security;

// =========================================
// IMPORTS BÁSICOS DO SPRING
// =========================================

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// BCrypt para criptografia de senha
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Configuração de segurança HTTP
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// Filtro de segurança (chain)
import org.springframework.security.web.SecurityFilterChain;

// =========================================
// IMPORTS IMPORTANTES (ADICIONADOS)
// =========================================

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// =========================================
// CLASSE DE CONFIGURAÇÃO
// =========================================
@Configuration
public class SecurityConfig {

    // =========================================
    // PASSWORD ENCODER (BCrypt)
    // =========================================
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        // Cria encoder para criptografar senhas no banco
        return new BCryptPasswordEncoder();
    }

    // =========================================
    // JWT FILTER (INJEÇÃO DO FILTRO)
    // =========================================

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Construtor para injetar o filtro JWT
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // =========================================
    // CONFIGURAÇÃO DE SEGURANÇA HTTP
    // =========================================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // =========================================
            // DESABILITA CSRF (API REST não precisa)
            // =========================================
            .csrf(csrf -> csrf.disable())

            // =========================================
            // DEFINE SESSÃO COMO STATELESS (JWT)
            // =========================================
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // =========================================
            // REGRAS DE AUTORIZAÇÃO
            // =========================================
            .authorizeHttpRequests(auth -> auth

                // Endpoints públicos (auth)
                .requestMatchers("/auth/**").permitAll()

                // Swagger liberado
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()

                // IMPORTANTE:
                // Agora medicamentos NÃO são mais públicos
                .requestMatchers("/medicamentos/**").authenticated()

                // Qualquer outra rota exige autenticação
                .anyRequest().authenticated()
            )

            // =========================================
            // ADICIONA O FILTRO JWT NA CADEIA DE SEGURANÇA
            // =========================================
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        // Retorna configuração final
        return http.build();
    }
}
