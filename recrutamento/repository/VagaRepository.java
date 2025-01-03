package com.pacto.recrutamento.repository;

import com.pacto.recrutamento.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findByAtivaTrue();
    List<Vaga> findByDepartamento(String departamento);
    List<Vaga> findByResponsavelId(Long responsavelId);
}
