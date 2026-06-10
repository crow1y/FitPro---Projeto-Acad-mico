package com.academia.controller;

import com.academia.dto.AlunoDTO;
import com.academia.model.Aluno;
import com.academia.repository.AlunoRepository;
import com.academia.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// CRUD de Alunos
@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired private AlunoRepository alunoRepository;
    @Autowired private PlanoRepository planoRepository;

    // GET /alunos → lista todos
    @GetMapping
    public List<Aluno> listar() {
        return alunoRepository.findAll();
    }

    // GET /alunos/ativos → lista só os ativos
    @GetMapping("/ativos")
    public List<Aluno> listarAtivos() {
        return alunoRepository.findByAtivo(true);
    }

    // GET /alunos/{id} → busca um pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscar(@PathVariable Long id) {
        return alunoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /alunos → cria novo (só ADMIN)
    @PostMapping
    public Aluno criar(@RequestBody AlunoDTO dados) {
        Aluno aluno = new Aluno();
        aluno.setNome(dados.nome());
        aluno.setEmail(dados.email());
        aluno.setTelefone(dados.telefone());

        if (dados.planoId() != null) {
            planoRepository.findById(dados.planoId())
                    .ifPresent(aluno::setPlano);
        }

        return alunoRepository.save(aluno);
    }

    // PUT /alunos/{id} → edita (só ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> editar(@PathVariable Long id, @RequestBody AlunoDTO dados) {
        return alunoRepository.findById(id).map(aluno -> {
            aluno.setNome(dados.nome());
            aluno.setEmail(dados.email());
            aluno.setTelefone(dados.telefone());

            if (dados.planoId() != null) {
                planoRepository.findById(dados.planoId())
                        .ifPresent(aluno::setPlano);
            }

            return ResponseEntity.ok(alunoRepository.save(aluno));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /alunos/{id} → inativa o aluno (só ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        return alunoRepository.findById(id).map(aluno -> {
            aluno.setAtivo(false);
            alunoRepository.save(aluno);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
