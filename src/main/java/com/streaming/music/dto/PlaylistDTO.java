package com.streaming.music.dto;

public class PlaylistDTO {
    private Long id;
    private String nome;
    private Long usuarioId;
    private String usuarioNome;

    public PlaylistDTO() {
    }

    public PlaylistDTO(Long id, String nome, Long usuarioId, String usuarioNome) {
        this.id = id;
        this.nome = nome;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }
}
