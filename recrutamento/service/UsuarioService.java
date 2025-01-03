package com.pacto.recrutamento.service;

import com.pacto.recrutamento.dto.UsuarioDTO;
import com.pacto.recrutamento.exception.BusinessException;
import com.pacto.recrutamento.exception.ResourceNotFoundException;
import com.pacto.recrutamento.model.Usuario;
import com.pacto.recrutamento.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        return toDTO(usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado")));
    }

    @Transactional
    public UsuarioDTO criar(UsuarioDTO dto, String senha) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setNome(dto.getNome());
        usuario.setCargo(dto.getCargo());
        usuario.setDepartamento(dto.getDepartamento());
        usuario.setRoles(dto.getRoles());

        return toDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setCargo(dto.getCargo());
        usuario.setDepartamento(dto.getDepartamento());
        usuario.setRoles(dto.getRoles());

        return toDTO(usuarioRepository.save(usuario));
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNome(usuario.getNome());
        dto.setCargo(usuario.getCargo());
        dto.setDepartamento(usuario.getDepartamento());
        dto.setRoles(usuario.getRoles());
        return dto;
    }
}
