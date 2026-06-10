package com.academia.model;

import jakarta.persistence.*;

// Representa um aluno matriculado na academia
@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private Boolean ativo = true;

    // Relacionamento: muitos alunos podem ter o mesmo plano
    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    public Aluno() {}

    public Long getId()       { return id; }
    public String getNome()   { return nome; }
    public String getEmail()  { return email; }
    public String getTelefone() { return telefone; }
    public Boolean getAtivo() { return ativo; }
    public Plano getPlano()   { return plano; }

    public void setNome(String nome)       { this.nome = nome; }
    public void setEmail(String email)     { this.email = email; }
    public void setTelefone(String t)      { this.telefone = t; }
    public void setAtivo(Boolean ativo)    { this.ativo = ativo; }
    public void setPlano(Plano plano)      { this.plano = plano; }
}
