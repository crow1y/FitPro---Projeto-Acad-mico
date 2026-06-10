package com.academia.controller;

import com.academia.dto.LoginDTO;
import com.academia.dto.TokenDTO;
import com.academia.model.Usuario;
import com.academia.repository.UsuarioRepository;
import com.academia.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

// Endpoints de autenticação — login e registro de usuários
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private TokenService tokenService;
    @Autowired private PasswordEncoder passwordEncoder;

    // POST /auth/login
    // Recebe login e senha, retorna um token JWT se as credenciais estiverem corretas
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dados) {
        var credenciais = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var auth = authManager.authenticate(credenciais); // lança exceção se inválido

        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(token));
    }

    // POST /auth/registrar
    // Cria um novo usuário no sistema com a senha criptografada
    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody LoginDTO dados) {
        if (usuarioRepository.findByLogin(dados.login()) != null) {
            return ResponseEntity.badRequest().body("Login já existe");
        }

        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        var novoUsuario = new Usuario(dados.login(), senhaCriptografada, "USER");
        usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok("Usuário criado com sucesso");
    }

    // POST /auth/registrar-admin
    // Cria um usuário administrador (use apenas para o primeiro setup)
    @PostMapping("/registrar-admin")
    public ResponseEntity registrarAdmin(@RequestBody LoginDTO dados) {
        if (usuarioRepository.findByLogin(dados.login()) != null) {
            return ResponseEntity.badRequest().body("Login já existe");
        }

        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        var novoAdmin = new Usuario(dados.login(), senhaCriptografada, "ADMIN");
        usuarioRepository.save(novoAdmin);

        return ResponseEntity.ok("Admin criado com sucesso");
    }
}
