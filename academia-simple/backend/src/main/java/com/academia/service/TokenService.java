package com.academia.service;

import com.academia.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

// Responsável por gerar e validar tokens JWT
@Service
public class TokenService {

    // Lê a chave secreta do application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    // Gera um token JWT para o usuário que acabou de fazer login
    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret); // algoritmo de assinatura

            return JWT.create()
                    .withIssuer("academia-api")           // quem gerou o token
                    .withSubject(usuario.getUsername())    // login do usuário dentro do token
                    .withExpiresAt(                        // token expira em 2 horas
                            LocalDateTime.now()
                                    .plusHours(2)
                                    .toInstant(ZoneOffset.of("-03:00"))
                    )
                    .sign(algoritmo);                      // assina com a chave secreta

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    // Valida o token e retorna o login do usuário
    // Se o token for inválido ou expirado, retorna string vazia
    public String validarToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer("academia-api")
                    .build()
                    .verify(token)      // lança exceção se inválido
                    .getSubject();      // retorna o login guardado no token

        } catch (Exception e) {
            return ""; // token inválido
        }
    }
}
