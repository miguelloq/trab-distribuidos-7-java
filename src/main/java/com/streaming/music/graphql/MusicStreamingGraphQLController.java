package com.streaming.music.graphql;

import com.streaming.music.dto.*;
import com.streaming.music.model.Musica;
import com.streaming.music.service.MusicaService;
import com.streaming.music.service.PlaylistService;
import com.streaming.music.service.UsuarioService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MusicStreamingGraphQLController {

    private final UsuarioService usuarioService;
    private final MusicaService musicaService;
    private final PlaylistService playlistService;

    public MusicStreamingGraphQLController(
        UsuarioService usuarioService,
        MusicaService musicaService,
        PlaylistService playlistService
    ) {
        this.usuarioService = usuarioService;
        this.musicaService = musicaService;
        this.playlistService = playlistService;
    }

    @QueryMapping
    public List<UsuarioDTO> usuarios() {
        return usuarioService.listarTodos();
    }

    @QueryMapping
    public List<MusicaDTO> musicas() {
        return musicaService.listarTodas();
    }

    @QueryMapping
    public MusicaDTO musicaPorId(@Argument Long id) {
        return musicaService.buscarPorId(id);
    }

    @QueryMapping
    public List<PlaylistDTO> playlistsPorUsuario(@Argument Long usuarioId) {
        return playlistService.listarPlaylistsPorUsuario(usuarioId);
    }

    @QueryMapping
    public PlaylistComMusicasDTO musicasDaPlaylist(@Argument Long playlistId) {
        return playlistService.listarMusicasDaPlaylist(playlistId);
    }

    @MutationMapping
    public MusicaDTO criarMusica(@Argument CriarMusicaInput input) {
        Musica musica = musicaService.criar(input.getNome(), input.getArtista());
        return new MusicaDTO(
            musica.getId(),
            musica.getNome(),
            musica.getArtista()
        );
    }

    @MutationMapping
    public MusicaDTO atualizarMusica(@Argument AtualizarMusicaInput input) {
        Musica musica = musicaService.atualizar(input.getId(), input.getNome(), input.getArtista());
        if (musica != null) {
            return new MusicaDTO(
                musica.getId(),
                musica.getNome(),
                musica.getArtista()
            );
        } else {
            return null;
        }
    }

    @MutationMapping
    public DeletarMusicaResponse deletarMusica(@Argument Long id) {
        boolean deletado = musicaService.deletar(id);
        return new DeletarMusicaResponse(
            deletado,
            deletado ? "Música deletada com sucesso" : "Música não encontrada"
        );
    }
}
