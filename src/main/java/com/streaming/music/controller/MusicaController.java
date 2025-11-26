package com.streaming.music.controller;

import com.streaming.music.dto.MusicaDTO;
import com.streaming.music.model.Musica;
import com.streaming.music.service.MusicaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    private final MusicaService musicaService;

    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    @GetMapping
    public ResponseEntity<List<MusicaDTO>> listarTodas() {
        List<MusicaDTO> musicas = musicaService.listarTodas();
        return ResponseEntity.ok(musicas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicaDTO> buscarPorId(@PathVariable Long id) {
        MusicaDTO musica = musicaService.buscarPorId(id);
        if (musica != null) {
            return ResponseEntity.ok(musica);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> criar(@RequestBody CriarMusicaRequest request) {
        Musica musica = musicaService.criar(request.getNome(), request.getArtista());
        Map<String, Object> response = new HashMap<>();
        response.put("id", musica.getId());
        response.put("nome", musica.getNome());
        response.put("artista", musica.getArtista());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizar(
        @PathVariable Long id,
        @RequestBody AtualizarMusicaRequest request
    ) {
        Musica musica = musicaService.atualizar(id, request.getNome(), request.getArtista());
        if (musica != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", musica.getId());
            response.put("nome", musica.getNome());
            response.put("artista", musica.getArtista());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
        boolean deletado = musicaService.deletar(id);
        if (deletado) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Música deletada com sucesso");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

class CriarMusicaRequest {
    private String nome;
    private String artista;

    public CriarMusicaRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
}

class AtualizarMusicaRequest {
    private String nome;
    private String artista;

    public AtualizarMusicaRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
}
