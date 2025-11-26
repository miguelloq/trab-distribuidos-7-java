package com.streaming.music.dto;

public class DeletarMusicaResponse {
    private Boolean sucesso;
    private String mensagem;

    public DeletarMusicaResponse() {
    }

    public DeletarMusicaResponse(Boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }

    public Boolean getSucesso() {
        return sucesso;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
