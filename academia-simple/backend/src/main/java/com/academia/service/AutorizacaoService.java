package com.academia.service;

import com.academia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// O Spring Security chama este serviço automaticamente quando alguém tenta se autenticar
// Ele pergunta: "como eu busco um usuário pelo login?"  — respondemos aqui
@Service
public class AutorizacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Busca o usuário no banco pelo login
        return repository.findByLogin(login);
    }
}
