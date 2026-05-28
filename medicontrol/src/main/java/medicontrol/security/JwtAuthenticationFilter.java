package medicontrol.security;

// ===============================
// IMPORTAÇÕES JAKARTA SERVLET
// ===============================

// Responsável por continuar a cadeia de filtros
import jakarta.servlet.FilterChain;

// Exceções de servlet
import jakarta.servlet.ServletException;

// Request HTTP
import jakarta.servlet.http.HttpServletRequest;

// Response HTTP
import jakarta.servlet.http.HttpServletResponse;

// ===============================
// IMPORTAÇÕES SPRING SECURITY
// ===============================

// Token de autenticação do Spring
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

// Contexto de autenticação atual
import org.springframework.security.core.context.SecurityContextHolder;

// Interface padrão de usuário autenticável
import org.springframework.security.core.userdetails.UserDetails;

// Serviço responsável por carregar usuário do banco
import org.springframework.security.core.userdetails.UserDetailsService;

// Detalhes da autenticação HTTP
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

// Marca a classe como componente gerenciado pelo Spring
import org.springframework.stereotype.Component;

// Filtro executado uma única vez por requisição
import org.springframework.web.filter.OncePerRequestFilter;

// ===============================
// IMPORTAÇÃO IO
// ===============================

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
 *
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
     * Este método impede que o filtro JWT
     * seja executado em rotas públicas.
     *
     * Sem isso:
     * → /auth/login retorna 403
     * → /auth/register retorna 403
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        // Obtém caminho da requisição
        String path = request.getServletPath();
        System.out.println("PATH: " + path);

        // Retorna TRUE para ignorar filtro
        return path.startsWith("/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }

    /**
     * =========================================
     * FILTRO PRINCIPAL JWT
     * =========================================
     *
     * Executado em TODAS requisições protegidas
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

        // Exemplo:
        // Authorization: Bearer eyJhbGciOi...
        final String authHeader =
                request.getHeader("Authorization");

        // Token JWT
        final String jwt;

        // Email extraído do token
        final String userEmail;

        // =========================================
        // VERIFICA SE EXISTE TOKEN
        // =========================================

        // Se não existir token:
        // → continua fluxo normal
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // =========================================
        // REMOVE "Bearer "
        // =========================================

        jwt = authHeader.substring(7);
        System.out.println("TOKEN RECEBIDO: " + jwt);

        // =========================================
        // EXTRAI EMAIL DO TOKEN
        // =========================================

        userEmail = jwtService.extractEmail(jwt);
        System.out.println("EMAIL EXTRAÍDO: " + userEmail);
        

        // =========================================
        // AUTENTICA USUÁRIO
        // =========================================

        // Só autentica se:
        // 1. Email existir
        // 2. Usuário ainda não autenticado
        if (userEmail != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            // Carrega usuário do banco
            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(userEmail);

            // =========================================
            // VALIDA TOKEN
            // =========================================

            // Verifica:
            // → token válido
            // → token pertence ao usuário
            // → token não expirou
            if (jwtService.isTokenValid(
                    jwt,
                    userDetails.getUsername()
                    
            )) {
                System.out.println("TOKEN VÁLIDO!");

                // =========================================
                // CRIA AUTENTICAÇÃO SPRING
                // =========================================

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(

                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Adiciona detalhes da request
                authToken.setDetails(

                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // =========================================
                // SALVA AUTENTICAÇÃO NO CONTEXTO
                // =========================================

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
