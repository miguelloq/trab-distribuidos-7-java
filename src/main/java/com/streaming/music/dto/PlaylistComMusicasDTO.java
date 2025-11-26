package com.streaming.music.dto;

import java.util.List;

public class PlaylistComMusicasDTO {
    private Long id;
    private String nome;
    private List<MusicaDTO> musicas;

    public PlaylistComMusicasDTO() {
    }

    public PlaylistComMusicasDTO(Long id, String nome, List<MusicaDTO> musicas) {
        this.id = id;
        this.nome = nome;
        this.musicas = musicas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<MusicaDTO> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<MusicaDTO> musicas) {
        this.musicas = musicas;
    }
}
