package com.pacto.recrutamento.service;

import com.pacto.recrutamento.dto.CandidaturaDTO;
import com.pacto.recrutamento.exception.BusinessException;
import com.pacto.recrutamento.exception.ResourceNotFoundException;
import com.pacto.recrutamento.model.Candidatura;
import com.pacto.recrutamento.model.Usuario;
import com.pacto.recrutamento.model.Vaga;
import com.pacto.recrutamento.repository.CandidaturaRepository;
import com.pacto.recrutamento.repository.UsuarioRepository;
import com.pacto.recrutamento.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<CandidaturaDTO> listarPorCandidato(Long candidatoId) {
        return candidaturaRepository.findByCandidatoId(candidatoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CandidaturaDTO> listarPorVaga(Long vagaId) {
        return candidaturaRepository.findByVagaId(vagaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CandidaturaDTO criar(CandidaturaDTO dto) {
        if (candidaturaRepository.existsByVagaIdAndCandidatoId(dto.getVagaId(), dto.getCandidatoId())) {
            throw new BusinessException("Candidatura já existe para esta vaga");
        }

        Vaga vaga = vagaRepository.findById(dto.getVagaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));

        if (!vaga.isAtiva()) {
            throw new BusinessException("Vaga não está mais ativa");
        }

        Usuario candidato = usuarioRepository.findById(dto.getCandidatoId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));

        Candidatura candidatura = new Candidatura();
        candidatura.setVaga(vaga);
        candidatura.setCandidato(candidato);
        candidatura.setObservacoes(dto.getObservacoes());

        return toDTO(candidaturaRepository.save(candidatura));
    }

    @Transactional
    public CandidaturaDTO atualizarStatus(Long id, CandidaturaDTO dto) {
        Candidatura candidatura = candidaturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidatura não encontrada"));

        candidatura.setStatus(dto.getStatus());
        candidatura.setObservacoes(dto.getObservacoes());

        return toDTO(candidaturaRepository.save(candidatura));
    }

    private CandidaturaDTO toDTO(Candidatura candidatura) {
        CandidaturaDTO dto = new CandidaturaDTO();
        dto.setId(candidatura.getId());
        dto.setVagaId(candidatura.getVaga().getId());
        dto.setCandidatoId(candidatura.getCandidato().getId());
        dto.setDataCandidatura(candidatura.getDataCandidatura());
        dto.setStatus(candidatura.getStatus());
        dto.setObservacoes(candidatura.getObservacoes());
        return dto;
    }
}
