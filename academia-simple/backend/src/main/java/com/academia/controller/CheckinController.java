package com.academia.controller;

import com.academia.model.Checkin;
import com.academia.repository.AlunoRepository;
import com.academia.repository.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

// Gerencia os check-ins (entradas dos alunos na academia)
@RestController
@RequestMapping("/checkins")
public class CheckinController {

    @Autowired private CheckinRepository checkinRepository;
    @Autowired private AlunoRepository alunoRepository;

    // GET /checkins → lista todos
    @GetMapping
    public List<Checkin> listar() {
        return checkinRepository.findAll();
    }

    // GET /checkins/aluno/{alunoId} → check-ins de um aluno específico
    @GetMapping("/aluno/{alunoId}")
    public List<Checkin> listarPorAluno(@PathVariable Long alunoId) {
        return checkinRepository.findByAlunoId(alunoId);
    }

    // POST /checkins?alunoId=1 → registra entrada do aluno agora
    @PostMapping
    public ResponseEntity<Checkin> registrar(@RequestParam Long alunoId) {
        return alunoRepository.findById(alunoId).map(aluno -> {
            Checkin checkin = new Checkin();
            checkin.setAluno(aluno);
            checkin.setDataHora(LocalDateTime.now()); // registra a hora atual
            return ResponseEntity.ok(checkinRepository.save(checkin));
        }).orElse(ResponseEntity.notFound().build());
    }
}
