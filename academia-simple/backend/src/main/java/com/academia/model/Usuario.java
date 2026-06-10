package com.academia.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

// Representa um usuário do sistema (quem faz login)
// Implementa UserDetails para o Spring Security saber como autenticar
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String login;
    private String senha;
    private String funcao; // "ADMIN" ou "USER"

    public Usuario() {}

    public Usuario(String login, String senha, String funcao) {
        this.login  = login;
        this.senha  = senha;
        this.funcao = funcao;
    }

    // Spring Security usa este método para saber o que o usuário pode fazer
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if ("ADMIN".equals(this.funcao)) {
            return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // Spring Security usa estes dois métodos para validar login e senha
    @Override public String getPassword() { return senha; }
    @Override public String getUsername()  { return login; }

    // Conta sempre válida (simplificado)
    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }

    // Getters
    public String getId()     { return id; }
    public String getLogin()  { return login; }
    public String getFuncao() { return funcao; }
}
