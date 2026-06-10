package com.academia.config;

import com.academia.repository.UsuarioRepository;
import com.academia.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Este filtro é executado em TODA requisição que chega na aplicação
// Ele verifica se existe um token JWT válido no cabeçalho da requisição
@Component
public class FiltroSeguranca extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Pega o token do cabeçalho "Authorization"
        String token = pegarToken(request);

        // 2. Se tiver token, valida e autentica o usuário
        if (token != null) {
            String login = tokenService.validarToken(token); // retorna o login ou ""
            if (!login.isEmpty()) {
                var usuario = usuarioRepository.findByLogin(login);

                // 3. Registra o usuário como autenticado no contexto do Spring Security
                var autenticacao = new UsernamePasswordAuthenticationToken(
                        usuario, null, usuario.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }
        }

        // 4. Continua o processamento da requisição
        filterChain.doFilter(request, response);
    }

    // Extrai o token do cabeçalho Authorization
    // O header vem assim: "Bearer eyJhbGciOiJ..."
    // Removemos "Bearer " para ficar só com o token
    private String pegarToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) return null;
        return header.replace("Bearer ", "").trim();
    }
}
