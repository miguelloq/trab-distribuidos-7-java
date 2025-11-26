package com.streaming.music.soap;

import com.streaming.music.dto.MusicaDTO;
import com.streaming.music.dto.PlaylistComMusicasDTO;
import com.streaming.music.dto.PlaylistDTO;
import com.streaming.music.dto.UsuarioDTO;
import com.streaming.music.model.Musica;
import com.streaming.music.service.MusicaService;
import com.streaming.music.service.PlaylistService;
import com.streaming.music.service.UsuarioService;
import jakarta.xml.bind.annotation.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class MusicStreamingSoapEndpoint {

    public static final String NAMESPACE_URI = "http://streaming.com/music/soap";

    private final UsuarioService usuarioService;
    private final MusicaService musicaService;
    private final PlaylistService playlistService;

    public MusicStreamingSoapEndpoint(
        UsuarioService usuarioService,
        MusicaService musicaService,
        PlaylistService playlistService
    ) {
        this.usuarioService = usuarioService;
        this.musicaService = musicaService;
        this.playlistService = playlistService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarUsuariosRequest")
    @ResponsePayload
    public ListarUsuariosResponse listarUsuarios(@RequestPayload ListarUsuariosRequest request) {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();

        ListarUsuariosResponse response = new ListarUsuariosResponse();
        List<UsuarioSoap> usuariosSoap = new ArrayList<>();
        for (UsuarioDTO usuario : usuarios) {
            UsuarioSoap usuarioSoap = new UsuarioSoap();
            usuarioSoap.setId(usuario.getId() != null ? usuario.getId() : 0);
            usuarioSoap.setNome(usuario.getNome());
            usuarioSoap.setIdade(usuario.getIdade());
            usuariosSoap.add(usuarioSoap);
        }
        response.setUsuarios(usuariosSoap);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarMusicasRequest")
    @ResponsePayload
    public ListarMusicasResponse listarMusicas(@RequestPayload ListarMusicasRequest request) {
        List<MusicaDTO> musicas = musicaService.listarTodas();

        ListarMusicasResponse response = new ListarMusicasResponse();
        List<MusicaSoap> musicasSoap = new ArrayList<>();
        for (MusicaDTO musica : musicas) {
            MusicaSoap musicaSoap = new MusicaSoap();
            musicaSoap.setId(musica.getId() != null ? musica.getId() : 0);
            musicaSoap.setNome(musica.getNome());
            musicaSoap.setArtista(musica.getArtista());
            musicasSoap.add(musicaSoap);
        }
        response.setMusicas(musicasSoap);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "buscarMusicaPorIdRequest")
    @ResponsePayload
    public BuscarMusicaPorIdResponse buscarMusicaPorId(@RequestPayload BuscarMusicaPorIdRequest request) {
        MusicaDTO musica = musicaService.buscarPorId(request.getMusicaId());

        BuscarMusicaPorIdResponse response = new BuscarMusicaPorIdResponse();
        if (musica != null) {
            MusicaSoap musicaSoap = new MusicaSoap();
            musicaSoap.setId(musica.getId() != null ? musica.getId() : 0);
            musicaSoap.setNome(musica.getNome());
            musicaSoap.setArtista(musica.getArtista());
            response.setMusica(musicaSoap);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "criarMusicaRequest")
    @ResponsePayload
    public CriarMusicaResponse criarMusica(@RequestPayload CriarMusicaRequest request) {
        Musica musica = musicaService.criar(request.getNome(), request.getArtista());

        CriarMusicaResponse response = new CriarMusicaResponse();
        MusicaSoap musicaSoap = new MusicaSoap();
        musicaSoap.setId(musica.getId() != null ? musica.getId() : 0);
        musicaSoap.setNome(musica.getNome());
        musicaSoap.setArtista(musica.getArtista());
        response.setMusica(musicaSoap);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "atualizarMusicaRequest")
    @ResponsePayload
    public AtualizarMusicaResponse atualizarMusica(@RequestPayload AtualizarMusicaRequest request) {
        Musica musica = musicaService.atualizar(request.getId(), request.getNome(), request.getArtista());

        AtualizarMusicaResponse response = new AtualizarMusicaResponse();
        if (musica != null) {
            MusicaSoap musicaSoap = new MusicaSoap();
            musicaSoap.setId(musica.getId() != null ? musica.getId() : 0);
            musicaSoap.setNome(musica.getNome());
            musicaSoap.setArtista(musica.getArtista());
            response.setMusica(musicaSoap);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletarMusicaRequest")
    @ResponsePayload
    public DeletarMusicaResponse deletarMusica(@RequestPayload DeletarMusicaRequest request) {
        boolean deletado = musicaService.deletar(request.getMusicaId());

        DeletarMusicaResponse response = new DeletarMusicaResponse();
        response.setSucesso(deletado);
        response.setMensagem(deletado ? "Música deletada com sucesso" : "Música não encontrada");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarPlaylistsPorUsuarioRequest")
    @ResponsePayload
    public ListarPlaylistsPorUsuarioResponse listarPlaylistsPorUsuario(@RequestPayload ListarPlaylistsPorUsuarioRequest request) {
        List<PlaylistDTO> playlists = playlistService.listarPlaylistsPorUsuario(request.getUsuarioId());

        ListarPlaylistsPorUsuarioResponse response = new ListarPlaylistsPorUsuarioResponse();
        List<PlaylistSoap> playlistsSoap = new ArrayList<>();
        for (PlaylistDTO playlist : playlists) {
            PlaylistSoap playlistSoap = new PlaylistSoap();
            playlistSoap.setId(playlist.getId() != null ? playlist.getId() : 0);
            playlistSoap.setNome(playlist.getNome());
            playlistSoap.setUsuarioId(playlist.getUsuarioId());
            playlistSoap.setUsuarioNome(playlist.getUsuarioNome());
            playlistsSoap.add(playlistSoap);
        }
        response.setPlaylists(playlistsSoap);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarMusicasDaPlaylistRequest")
    @ResponsePayload
    public ListarMusicasDaPlaylistResponse listarMusicasDaPlaylist(@RequestPayload ListarMusicasDaPlaylistRequest request) {
        PlaylistComMusicasDTO playlist = playlistService.listarMusicasDaPlaylist(request.getPlaylistId());

        ListarMusicasDaPlaylistResponse response = new ListarMusicasDaPlaylistResponse();
        if (playlist != null) {
            PlaylistComMusicasSoap playlistSoap = new PlaylistComMusicasSoap();
            playlistSoap.setId(playlist.getId() != null ? playlist.getId() : 0);
            playlistSoap.setNome(playlist.getNome());

            List<MusicaSoap> musicasSoap = new ArrayList<>();
            for (MusicaDTO musica : playlist.getMusicas()) {
                MusicaSoap musicaSoap = new MusicaSoap();
                musicaSoap.setId(musica.getId() != null ? musica.getId() : 0);
                musicaSoap.setNome(musica.getNome());
                musicaSoap.setArtista(musica.getArtista());
                musicasSoap.add(musicaSoap);
            }
            playlistSoap.setMusicas(musicasSoap);
            response.setPlaylist(playlistSoap);
        }
        return response;
    }
}

@XmlRootElement(name = "listarUsuariosRequest", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class ListarUsuariosRequest {
}

@XmlRootElement(name = "listarUsuariosResponse", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class ListarUsuariosResponse {
    @XmlElement(name = "usuarios")
    private List<UsuarioSoap> usuarios = new ArrayList<>();

    public List<UsuarioSoap> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioSoap> usuarios) {
        this.usuarios = usuarios;
    }
}

@XmlRootElement(name = "listarMusicasRequest", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class ListarMusicasRequest {
}

@XmlRootElement(name = "listarMusicasResponse", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class ListarMusicasResponse {
    @XmlElement(name = "musicas")
    private List<MusicaSoap> musicas = new ArrayList<>();

    public List<MusicaSoap> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<MusicaSoap> musicas) {
        this.musicas = musicas;
    }
}

@XmlRootElement(name = "buscarMusicaPorIdRequest", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class BuscarMusicaPorIdRequest {
    @XmlElement(required = true)
    private long musicaId;

    public long getMusicaId() {
        return musicaId;
    }

    public void setMusicaId(long musicaId) {
        this.musicaId = musicaId;
    }
}

@XmlRootElement(name = "buscarMusicaPorIdResponse", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class BuscarMusicaPorIdResponse {
    @XmlElement(name = "musica")
    private MusicaSoap musica;

    public MusicaSoap getMusica() {
        return musica;
    }

    public void setMusica(MusicaSoap musica) {
        this.musica = musica;
    }
}

@XmlRootElement(name = "criarMusicaRequest", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class CriarMusicaRequest {
    @XmlElement(required = true)
    private String nome;
    @XmlElement(required = true)
    private String artista;

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

@XmlRootElement(name = "criarMusicaResponse", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class CriarMusicaResponse {
    @XmlElement(name = "musica")
    private MusicaSoap musica;

    public MusicaSoap getMusica() {
        return musica;
    }

    public void setMusica(MusicaSoap musica) {
        this.musica = musica;
    }
}

@XmlRootElement(name = "atualizarMusicaRequest", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class AtualizarMusicaRequest {
    @XmlElement(required = true)
    private long id;
    @XmlElement(required = true)
    private String nome;
    @XmlElement(required = true)
    private String artista;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

@XmlRootElement(name = "atualizarMusicaResponse", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class AtualizarMusicaResponse {
    @XmlElement(name = "musica")
    private MusicaSoap musica;

    public MusicaSoap getMusica() {
        return musica;
    }

    public void setMusica(MusicaSoap musica) {
        this.musica = musica;
    }
}

@XmlRootElement(name = "deletarMusicaRequest", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class DeletarMusicaRequest {
    @XmlElement(required = true)
    private long musicaId;

    public long getMusicaId() {
        return musicaId;
    }

    public void setMusicaId(long musicaId) {
        this.musicaId = musicaId;
    }
}

@XmlRootElement(name = "deletarMusicaResponse", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class DeletarMusicaResponse {
    @XmlElement(required = true)
    private boolean sucesso;
    @XmlElement(required = true)
    private String mensagem;

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}

@XmlRootElement(name = "listarPlaylistsPorUsuarioRequest", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class ListarPlaylistsPorUsuarioRequest {
    @XmlElement(required = true)
    private long usuarioId;

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

@XmlRootElement(name = "listarPlaylistsPorUsuarioResponse", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class ListarPlaylistsPorUsuarioResponse {
    @XmlElement(name = "playlists")
    private List<PlaylistSoap> playlists = new ArrayList<>();

    public List<PlaylistSoap> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistSoap> playlists) {
        this.playlists = playlists;
    }
}

@XmlRootElement(name = "listarMusicasDaPlaylistRequest", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class ListarMusicasDaPlaylistRequest {
    @XmlElement(required = true)
    private long playlistId;

    public long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(long playlistId) {
        this.playlistId = playlistId;
    }
}

@XmlRootElement(name = "listarMusicasDaPlaylistResponse", namespace = MusicStreamingSoapEndpoint.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
class ListarMusicasDaPlaylistResponse {
    @XmlElement(name = "playlist")
    private PlaylistComMusicasSoap playlist;

    public PlaylistComMusicasSoap getPlaylist() {
        return playlist;
    }

    public void setPlaylist(PlaylistComMusicasSoap playlist) {
        this.playlist = playlist;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class UsuarioSoap {
    private long id;
    private String nome;
    private int idade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class MusicaSoap {
    private long id;
    private String nome;
    private String artista;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

@XmlAccessorType(XmlAccessType.FIELD)
class PlaylistSoap {
    private long id;
    private String nome;
    private long usuarioId;
    private String usuarioNome;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class PlaylistComMusicasSoap {
    private long id;
    private String nome;
    @XmlElement(name = "musicas")
    private List<MusicaSoap> musicas = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<MusicaSoap> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<MusicaSoap> musicas) {
        this.musicas = musicas;
    }
}
