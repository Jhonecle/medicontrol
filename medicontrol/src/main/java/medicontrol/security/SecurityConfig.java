package medicontrol.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * =========================================
 * CONFIGURAÇÃO DE SEGURANÇA
 * =========================================
 *
 * Responsável por definir:
 *
 * 1. Quais rotas são públicas
 * 2. Quais rotas precisam de autenticação
 * 3. Como as senhas são criptografadas
 * 4. Como o JWT é processado
 * 5. Política de sessão (Stateless)
 */
@Configuration
public class SecurityConfig {

    // =========================================
    // JWT FILTER
    // =========================================

    /**
     * Filtro JWT injetado via construtor.
     * Intercepta requisições e valida o token.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // =========================================
    // PASSWORD ENCODER
    // =========================================

    /**
     * Bean responsável por criptografar senhas.
     *
     * BCrypt é o algoritmo recomendado:
     * → Adiciona salt automático
     * → Resistente a ataques de força bruta
     * → Padrão do mercado
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // =========================================
    // AUTHENTICATION MANAGER
    // =========================================

    /**
     * Bean do gerenciador de autenticação.
     * Usado internamente pelo Spring Security
     * para processar autenticações.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    // =========================================
    // SECURITY FILTER CHAIN
    // =========================================

    /**
     * Define as regras de segurança da aplicação.
     *
     * Fluxo de uma requisição:
     * Request → JWT Filter → Regras de Auth → Controller
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // =========================================
                // CSRF DESABILITADO
                // =========================================
                // APIs REST não usam sessão/cookies,
                // portanto CSRF não é necessário.
                .csrf(csrf -> csrf.disable())

                // =========================================
                // SESSÃO STATELESS
                // =========================================
                // Cada requisição é independente.
                // O estado fica no token JWT,
                // não no servidor.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // =========================================
                // REGRAS DE AUTORIZAÇÃO
                // =========================================
                .authorizeHttpRequests(auth -> auth

                        // =========================================
                        // ROTAS PÚBLICAS — AUTH + ERROR
                        // =========================================
                        // Libera /auth/** e /error sem token.
                        // /error é necessário para respostas
                        // de erro não gerarem 403 em cascata.
                        .requestMatchers("/auth/**", "/error").permitAll()

                        // =========================================
                        // ROTAS PÚBLICAS — SWAGGER
                        // =========================================
                        // Documentação acessível sem token
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // =========================================
                        // DEMAIS ROTAS — PROTEGIDAS
                        // =========================================
                        // Exige token JWT válido no header:
                        // Authorization: Bearer <token>
                        .anyRequest().authenticated()
                )

                // =========================================
                // FILTRO JWT
                // =========================================
                // Adicionado antes do filtro padrão
                // do Spring. Ordem importa — JWT primeiro.
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}