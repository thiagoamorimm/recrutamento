package com.pacto.recrutamento.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vagas")
public class Vaga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(nullable = false, length = 2000)
    private String descricao;
    
    @Column(nullable = false)
    private String departamento;
    
    @Column(nullable = false)
    private String nivelSenioridade;
    
    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column
    private LocalDateTime dataEncerramento;
    
    @Column(nullable = false)
    private boolean ativa = true;
    
    @ElementCollection
    @CollectionTable(name = "vaga_requisitos", joinColumns = @JoinColumn(name = "vaga_id"))
    @Column(name = "requisito")
    private Set<String> requisitos = new HashSet<>();
    
    @OneToMany(mappedBy = "vaga")
    private Set<Candidatura> candidaturas = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "responsavel_id", nullable = false)
    private Usuario responsavel;
}
