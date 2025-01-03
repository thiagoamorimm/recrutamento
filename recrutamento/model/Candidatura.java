package com.pacto.recrutamento.model;

import com.pacto.recrutamento.enums.StatusCandidatura;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "candidaturas")
public class Candidatura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;
    
    @ManyToOne
    @JoinColumn(name = "candidato_id", nullable = false)
    private Usuario candidato;
    
    @Column(nullable = false)
    private LocalDateTime dataCandidatura = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCandidatura status = StatusCandidatura.PENDENTE;
    
    @Column(length = 1000)
    private String observacoes;
}
