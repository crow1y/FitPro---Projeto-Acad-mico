package com.academia.repository;

import com.academia.model.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CheckinRepository extends JpaRepository<Checkin, Long> {

    // Busca todos os check-ins de um aluno específico pelo ID
    List<Checkin> findByAlunoId(Long alunoId);
}
