package com.academia.dto;

// Dados recebidos ao criar ou editar um aluno
public record AlunoDTO(String nome, String email, String telefone, Long planoId) {}
