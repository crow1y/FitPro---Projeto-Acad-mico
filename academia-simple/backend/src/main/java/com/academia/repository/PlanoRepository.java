package com.academia.repository;

import com.academia.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD completo de Plano já vem do JpaRepository — nada extra necessário
public interface PlanoRepository extends JpaRepository<Plano, Long> {}
