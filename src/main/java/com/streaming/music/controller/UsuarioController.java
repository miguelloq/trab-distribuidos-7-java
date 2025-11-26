package com.streaming.music.controller;

import com.streaming.music.dto.UsuarioDTO;
import com.streaming.music.model.Usuario;
import com.streaming.music.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> criar(@RequestBody CriarUsuarioRequest request) {
        Usuario usuario = usuarioService.criar(request.getNome(), request.getIdade());
        Map<String, Object> response = new HashMap<>();
        response.put("id", usuario.getId());
        response.put("nome", usuario.getNome());
        response.put("idade", usuario.getIdade());
        return ResponseEntity.ok(response);
    }
}

class CriarUsuarioRequest {
    private String nome;
    private Integer idade;

    public CriarUsuarioRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}
