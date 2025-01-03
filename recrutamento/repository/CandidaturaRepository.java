package com.pacto.recrutamento.repository;

import com.pacto.recrutamento.model.Candidatura;
import com.pacto.recrutamento.enums.StatusCandidatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    List<Candidatura> findByCandidatoId(Long candidatoId);
    List<Candidatura> findByVagaId(Long vagaId);
    List<Candidatura> findByCandidatoIdAndStatus(Long candidatoId, StatusCandidatura status);
    boolean existsByVagaIdAndCandidatoId(Long vagaId, Long candidatoId);
}
