package com.pacto.recrutamento.controller;

import com.pacto.recrutamento.dto.VagaDTO;
import com.pacto.recrutamento.service.VagaService;
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
@RequestMapping("/api/vagas")
@Tag(name = "Vagas", description = "Endpoints de gerenciamento de vagas")
@SecurityRequirement(name = "bearer-key")
public class VagaController {

    @Autowired
    private VagaService vagaService;

    @Operation(summary = "Listar todas as vagas ativas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de vagas retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping
    public ResponseEntity<List<VagaDTO>> listarVagasAtivas() {
        return ResponseEntity.ok(vagaService.listarVagasAtivas());
    }

    @Operation(summary = "Buscar vaga por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vaga encontrada"),
        @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VagaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vagaService.buscarPorId(id));
    }

    @Operation(summary = "Criar nova vaga")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vaga criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para criar vaga")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaDTO> criar(@Valid @RequestBody VagaDTO vagaDTO) {
        return ResponseEntity.ok(vagaService.criar(vagaDTO));
    }

    @Operation(summary = "Atualizar vaga existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vaga atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para atualizar vaga"),
        @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody VagaDTO vagaDTO) {
        return ResponseEntity.ok(vagaService.atualizar(id, vagaDTO));
    }
}
