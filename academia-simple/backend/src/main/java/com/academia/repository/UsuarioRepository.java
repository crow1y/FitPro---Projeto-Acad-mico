package com.academia.repository;

import com.academia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

// O JpaRepository já fornece save(), findAll(), findById(), delete()...
// Só precisamos declarar métodos extras que o JPA entende pelo nome
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // "findByLogin" → JPA gera automaticamente: SELECT * FROM usuario WHERE login = ?
    UserDetails findByLogin(String login);
}
