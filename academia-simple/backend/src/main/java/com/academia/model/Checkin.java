package com.academia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Representa a entrada de um aluno na academia
@Entity
@Table(name = "checkin")
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento: um aluno pode ter muitos check-ins
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    private LocalDateTime dataHora;

    public Checkin() {}

    public Long getId()              { return id; }
    public Aluno getAluno()          { return aluno; }
    public LocalDateTime getDataHora() { return dataHora; }

    public void setAluno(Aluno aluno)        { this.aluno = aluno; }
    public void setDataHora(LocalDateTime d) { this.dataHora = d; }
}
