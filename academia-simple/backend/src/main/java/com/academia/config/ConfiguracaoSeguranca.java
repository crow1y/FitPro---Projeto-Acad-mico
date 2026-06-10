package com.academia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {

    @Autowired
    private FiltroSeguranca filtroSeguranca;

    @Bean
    public SecurityFilterChain seguranca(HttpSecurity http) throws Exception {
        return http
            // Desabilita CSRF — não precisamos pois usamos JWT (sem cookies de sessão)
            .csrf(csrf -> csrf.disable())

            // Libera o CORS para o front-end conseguir se comunicar com o back-end
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Stateless: o servidor não guarda sessão, cada requisição traz o token
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Define quais endpoints precisam de autenticação
            .authorizeHttpRequests(auth -> auth

                // Endpoints públicos (não precisam de token)
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/registrar").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/registrar-admin").permitAll()

                // Apenas ADMIN pode criar, editar e deletar
                .requestMatchers(HttpMethod.POST,   "/alunos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/alunos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/alunos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,   "/planos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/planos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/planos/**").hasRole("ADMIN")

                // Todo o resto exige apenas estar autenticado (qualquer role)
                .anyRequest().authenticated()
            )

            // Adiciona nosso filtro JWT antes do filtro padrão do Spring
            .addFilterBefore(filtroSeguranca, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    // Configuração de CORS — permite que o front-end acesse o back-end
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*")); // permite qualquer origem
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // Expõe o AuthenticationManager para usar no controller de login
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // BCrypt é o algoritmo padrão para criptografar senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
