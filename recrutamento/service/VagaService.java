package com.pacto.recrutamento.service;

import com.pacto.recrutamento.dto.VagaDTO;
import com.pacto.recrutamento.exception.ResourceNotFoundException;
import com.pacto.recrutamento.model.Usuario;
import com.pacto.recrutamento.model.Vaga;
import com.pacto.recrutamento.repository.UsuarioRepository;
import com.pacto.recrutamento.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<VagaDTO> listarVagasAtivas() {
        return vagaRepository.findByAtivaTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VagaDTO buscarPorId(Long id) {
        return toDTO(vagaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada")));
    }

    @Transactional
    public VagaDTO criar(VagaDTO dto) {
        Usuario responsavel = usuarioRepository.findById(dto.getResponsavelId())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado"));

        Vaga vaga = new Vaga();
        vaga.setTitulo(dto.getTitulo());
        vaga.setDescricao(dto.getDescricao());
        vaga.setDepartamento(dto.getDepartamento());
        vaga.setNivelSenioridade(dto.getNivelSenioridade());
        vaga.setDataEncerramento(dto.getDataEncerramento());
        vaga.setAtiva(true);
        vaga.setRequisitos(dto.getRequisitos());
        vaga.setResponsavel(responsavel);

        return toDTO(vagaRepository.save(vaga));
    }

    @Transactional
    public VagaDTO atualizar(Long id, VagaDTO dto) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));

        vaga.setTitulo(dto.getTitulo());
        vaga.setDescricao(dto.getDescricao());
        vaga.setDepartamento(dto.getDepartamento());
        vaga.setNivelSenioridade(dto.getNivelSenioridade());
        vaga.setDataEncerramento(dto.getDataEncerramento());
        vaga.setAtiva(dto.isAtiva());
        vaga.setRequisitos(dto.getRequisitos());

        return toDTO(vagaRepository.save(vaga));
    }

    private VagaDTO toDTO(Vaga vaga) {
        VagaDTO dto = new VagaDTO();
        dto.setId(vaga.getId());
        dto.setTitulo(vaga.getTitulo());
        dto.setDescricao(vaga.getDescricao());
        dto.setDepartamento(vaga.getDepartamento());
        dto.setNivelSenioridade(vaga.getNivelSenioridade());
        dto.setDataCriacao(vaga.getDataCriacao());
        dto.setDataEncerramento(vaga.getDataEncerramento());
        dto.setAtiva(vaga.isAtiva());
        dto.setRequisitos(vaga.getRequisitos());
        dto.setResponsavelId(vaga.getResponsavel().getId());
        return dto;
    }
}
