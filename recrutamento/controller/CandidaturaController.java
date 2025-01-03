package com.pacto.recrutamento.controller;

import com.pacto.recrutamento.dto.CandidaturaDTO;
import com.pacto.recrutamento.service.CandidaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/candidaturas")
@Tag(name = "Candidaturas", description = "Endpoints de gerenciamento de candidaturas")
@SecurityRequirement(name = "bearer-key")
public class CandidaturaController {

    @Autowired
    private CandidaturaService candidaturaService;

    @Operation(summary = "Listar candidaturas por candidato")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de candidaturas retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado"),
        @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @GetMapping("/candidato/{candidatoId}")
    public ResponseEntity<List<CandidaturaDTO>> listarPorCandidato(@PathVariable Long candidatoId) {
        return ResponseEntity.ok(candidaturaService.listarPorCandidato(candidatoId));
    }

    @Operation(summary = "Listar candidaturas por vaga")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de candidaturas retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para visualizar candidaturas"),
        @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @GetMapping("/vaga/{vagaId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CandidaturaDTO>> listarPorVaga(@PathVariable Long vagaId) {
        return ResponseEntity.ok(candidaturaService.listarPorVaga(vagaId));
    }

    @Operation(summary = "Criar nova candidatura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Candidatura criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou candidatura já existe"),
        @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @PostMapping
    public ResponseEntity<CandidaturaDTO> criar(@Valid @RequestBody CandidaturaDTO candidaturaDTO) {
        return ResponseEntity.ok(candidaturaService.criar(candidaturaDTO));
    }

    @Operation(summary = "Atualizar status da candidatura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para atualizar status"),
        @ApiResponse(responseCode = "404", description = "Candidatura não encontrada")
    })
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CandidaturaDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody CandidaturaDTO candidaturaDTO) {
        return ResponseEntity.ok(candidaturaService.atualizarStatus(id, candidaturaDTO));
    }
}
