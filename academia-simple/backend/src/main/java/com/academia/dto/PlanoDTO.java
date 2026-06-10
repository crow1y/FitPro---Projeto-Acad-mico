package com.academia.dto;

import java.math.BigDecimal;

// Dados recebidos ao criar ou editar um plano
public record PlanoDTO(String nome, BigDecimal valor) {}
