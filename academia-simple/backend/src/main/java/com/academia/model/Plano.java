package com.academia.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

// Representa um plano disponível na academia (ex: Mensal, Anual)
@Entity
@Table(name = "plano")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal valor;

    public Plano() {}

    public Long getId()        { return id; }
    public String getNome()    { return nome; }
    public BigDecimal getValor() { return valor; }

    public void setNome(String nome)       { this.nome = nome; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}
