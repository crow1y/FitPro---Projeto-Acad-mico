package com.academia.repository;

import com.academia.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    // Busca todos os alunos com ativo = true
    List<Aluno> findByAtivo(Boolean ativo);
}
