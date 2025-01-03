package com.pacto.recrutamento.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UsuarioDTO {
    private Long id;
    private String email;
    private String nome;
    private String cargo;
    private String departamento;
    private Set<String> roles;
}
