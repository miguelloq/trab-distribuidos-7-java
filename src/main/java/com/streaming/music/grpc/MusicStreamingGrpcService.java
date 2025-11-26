package com.streaming.music.grpc;

import com.streaming.music.dto.MusicaDTO;
import com.streaming.music.dto.PlaylistComMusicasDTO;
import com.streaming.music.dto.PlaylistDTO;
import com.streaming.music.dto.UsuarioDTO;
import com.streaming.music.grpc.proto.*;
import com.streaming.music.model.Musica;
import com.streaming.music.service.MusicaService;
import com.streaming.music.service.PlaylistService;
import com.streaming.music.service.UsuarioService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class MusicStreamingGrpcService extends MusicStreamingServiceGrpc.MusicStreamingServiceImplBase {

    private final UsuarioService usuarioService;
    private final MusicaService musicaService;
    private final PlaylistService playlistService;

    public MusicStreamingGrpcService(
        UsuarioService usuarioService,
        MusicaService musicaService,
        PlaylistService playlistService
    ) {
        this.usuarioService = usuarioService;
        this.musicaService = musicaService;
        this.playlistService = playlistService;
    }

    @Override
    public void listarUsuarios(Empty request, StreamObserver<ListaUsuariosResponse> responseObserver) {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();

        ListaUsuariosResponse.Builder responseBuilder = ListaUsuariosResponse.newBuilder();
        for (UsuarioDTO usuario : usuarios) {
            UsuarioProto usuarioProto = UsuarioProto.newBuilder()
                .setId(usuario.getId() != null ? usuario.getId() : 0)
                .setNome(usuario.getNome())
                .setIdade(usuario.getIdade())
                .build();
            responseBuilder.addUsuarios(usuarioProto);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void listarMusicas(Empty request, StreamObserver<ListaMusicasResponse> responseObserver) {
        List<MusicaDTO> musicas = musicaService.listarTodas();

        ListaMusicasResponse.Builder responseBuilder = ListaMusicasResponse.newBuilder();
        for (MusicaDTO musica : musicas) {
            MusicaProto musicaProto = MusicaProto.newBuilder()
                .setId(musica.getId() != null ? musica.getId() : 0)
                .setNome(musica.getNome())
                .setArtista(musica.getArtista())
                .build();
            responseBuilder.addMusicas(musicaProto);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscarMusicaPorId(MusicaIdRequest request, StreamObserver<MusicaProto> responseObserver) {
        MusicaDTO musica = musicaService.buscarPorId(request.getMusicaId());

        if (musica != null) {
            MusicaProto response = MusicaProto.newBuilder()
                .setId(musica.getId() != null ? musica.getId() : 0)
                .setNome(musica.getNome())
                .setArtista(musica.getArtista())
                .build();
            responseObserver.onNext(response);
        } else {
            responseObserver.onNext(MusicaProto.newBuilder().build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void criarMusica(CriarMusicaRequest request, StreamObserver<MusicaProto> responseObserver) {
        Musica musica = musicaService.criar(request.getNome(), request.getArtista());

        MusicaProto response = MusicaProto.newBuilder()
            .setId(musica.getId() != null ? musica.getId() : 0)
            .setNome(musica.getNome())
            .setArtista(musica.getArtista())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizarMusica(AtualizarMusicaRequest request, StreamObserver<MusicaProto> responseObserver) {
        Musica musica = musicaService.atualizar(request.getId(), request.getNome(), request.getArtista());

        if (musica != null) {
            MusicaProto response = MusicaProto.newBuilder()
                .setId(musica.getId() != null ? musica.getId() : 0)
                .setNome(musica.getNome())
                .setArtista(musica.getArtista())
                .build();
            responseObserver.onNext(response);
        } else {
            responseObserver.onNext(MusicaProto.newBuilder().build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deletarMusica(MusicaIdRequest request, StreamObserver<DeletarMusicaResponse> responseObserver) {
        boolean deletado = musicaService.deletar(request.getMusicaId());

        DeletarMusicaResponse response = DeletarMusicaResponse.newBuilder()
            .setSucesso(deletado)
            .setMensagem(deletado ? "Música deletada com sucesso" : "Música não encontrada")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void listarPlaylistsPorUsuario(
        UsuarioIdRequest request,
        StreamObserver<ListaPlaylistsResponse> responseObserver
    ) {
        List<PlaylistDTO> playlists = playlistService.listarPlaylistsPorUsuario(request.getUsuarioId());

        ListaPlaylistsResponse.Builder responseBuilder = ListaPlaylistsResponse.newBuilder();
        for (PlaylistDTO playlist : playlists) {
            PlaylistProto playlistProto = PlaylistProto.newBuilder()
                .setId(playlist.getId() != null ? playlist.getId() : 0)
                .setNome(playlist.getNome())
                .setUsuarioId(playlist.getUsuarioId())
                .setUsuarioNome(playlist.getUsuarioNome())
                .build();
            responseBuilder.addPlaylists(playlistProto);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void listarMusicasDaPlaylist(
        PlaylistIdRequest request,
        StreamObserver<PlaylistComMusicasProto> responseObserver
    ) {
        PlaylistComMusicasDTO playlist = playlistService.listarMusicasDaPlaylist(request.getPlaylistId());

        if (playlist != null) {
            PlaylistComMusicasProto.Builder responseBuilder = PlaylistComMusicasProto.newBuilder()
                .setId(playlist.getId() != null ? playlist.getId() : 0)
                .setNome(playlist.getNome());

            for (MusicaDTO musica : playlist.getMusicas()) {
                MusicaProto musicaProto = MusicaProto.newBuilder()
                    .setId(musica.getId() != null ? musica.getId() : 0)
                    .setNome(musica.getNome())
                    .setArtista(musica.getArtista())
                    .build();
                responseBuilder.addMusicas(musicaProto);
            }

            responseObserver.onNext(responseBuilder.build());
        } else {
            responseObserver.onNext(PlaylistComMusicasProto.newBuilder().build());
        }

        responseObserver.onCompleted();
    }
}
