package com.academia.dto;

// DTO = Data Transfer Object
// São objetos simples usados para receber dados nas requisições HTTP

// Dados recebidos no login
public record LoginDTO(String login, String senha) {}
