package com.academia.controller;

import com.academia.dto.PlanoDTO;
import com.academia.model.Plano;
import com.academia.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// CRUD de Planos
@RestController
@RequestMapping("/planos")
public class PlanoController {

    @Autowired private PlanoRepository planoRepository;

    // GET /planos
    @GetMapping
    public List<Plano> listar() {
        return planoRepository.findAll();
    }

    // GET /planos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Plano> buscar(@PathVariable Long id) {
        return planoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /planos (só ADMIN)
    @PostMapping
    public Plano criar(@RequestBody PlanoDTO dados) {
        Plano plano = new Plano();
        plano.setNome(dados.nome());
        plano.setValor(dados.valor());
        return planoRepository.save(plano);
    }

    // PUT /planos/{id} (só ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<Plano> editar(@PathVariable Long id, @RequestBody PlanoDTO dados) {
        return planoRepository.findById(id).map(plano -> {
            plano.setNome(dados.nome());
            plano.setValor(dados.valor());
            return ResponseEntity.ok(planoRepository.save(plano));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /planos/{id} (só ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        if (!planoRepository.existsById(id)) return ResponseEntity.notFound().build();
        planoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
