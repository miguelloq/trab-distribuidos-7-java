package com.streaming.music.service;

import com.streaming.music.dto.UsuarioDTO;
import com.streaming.music.model.Usuario;
import com.streaming.music.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
            .map(usuario -> new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getIdade()
            ))
            .collect(Collectors.toList());
    }

    @Transactional
    public Usuario criar(String nome, Integer idade) {
        Usuario usuario = new Usuario(nome, idade);
        return usuarioRepository.save(usuario);
    }
}
