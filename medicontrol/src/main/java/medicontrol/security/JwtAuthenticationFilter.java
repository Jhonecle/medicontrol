package medicontrol.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * =========================================
 * FILTRO JWT
 * =========================================
 *
 * Responsável por:
 *
 * 1. Interceptar todas requisições HTTP
 * 2. Ler token JWT do Header Authorization
 * 3. Validar token
 * 4. Identificar usuário autenticado
 * 5. Inserir autenticação no Spring Security
 *
 * Fluxo:
 *
 * Request → JWT Filter → SecurityContext → Controller
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Serviço JWT
    private final JwtService jwtService;

    // Serviço de usuários
    private final UserDetailsService userDetailsService;

    /**
     * =========================================
     * CONSTRUTOR
     * =========================================
     *
     * Injeção de dependência automática
     */
    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * =========================================
     * IGNORA ROTAS PÚBLICAS
     * =========================================
     *
     * Impede que o filtro JWT seja executado
     * em rotas públicas como /auth/login
     * e /auth/register.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        // Retorna TRUE para ignorar o filtro
        return path.startsWith("/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }

    /**
     * =========================================
     * FILTRO PRINCIPAL JWT
     * =========================================
     *
     * Executado em todas as requisições protegidas.
     * Valida o token e autentica o usuário
     * no contexto do Spring Security.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // =========================================
        // OBTÉM HEADER AUTHORIZATION
        // =========================================
        // Exemplo: Authorization: Bearer eyJhbGciOi...
        final String authHeader =
                request.getHeader("Authorization");

        final String jwt;
        final String userEmail;

        // =========================================
        // VERIFICA SE EXISTE TOKEN
        // =========================================
        // Se não existir, continua sem autenticar
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // =========================================
        // EXTRAI TOKEN E EMAIL
        // =========================================
        // Remove o prefixo "Bearer " do header
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractEmail(jwt);

        // =========================================
        // AUTENTICA USUÁRIO
        // =========================================
        // Só autentica se:
        // 1. Email existir no token
        // 2. Usuário ainda não estiver autenticado
        if (userEmail != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            // Carrega usuário do banco pelo email
            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(userEmail);

            // =========================================
            // VALIDA TOKEN
            // =========================================
            // Verifica assinatura, email e expiração
            if (jwtService.isTokenValid(
                    jwt, userDetails.getUsername())) {

                // =========================================
                // REGISTRA AUTENTICAÇÃO NO CONTEXTO
                // =========================================
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        // =========================================
        // CONTINUA FLUXO DA REQUISIÇÃO
        // =========================================
        filterChain.doFilter(request, response);
    }
}
