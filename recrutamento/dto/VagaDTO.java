package com.pacto.recrutamento.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class VagaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String departamento;
    private String nivelSenioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEncerramento;
    private boolean ativa;
    private Set<String> requisitos;
    private Long responsavelId;
}
