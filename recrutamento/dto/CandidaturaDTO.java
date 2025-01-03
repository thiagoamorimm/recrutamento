package com.pacto.recrutamento.dto;

import com.pacto.recrutamento.enums.StatusCandidatura;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CandidaturaDTO {
    private Long id;
    private Long vagaId;
    private Long candidatoId;
    private LocalDateTime dataCandidatura;
    private StatusCandidatura status;
    private String observacoes;
}
