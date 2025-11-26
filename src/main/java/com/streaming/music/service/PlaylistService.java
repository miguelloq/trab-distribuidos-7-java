package com.streaming.music.service;

import com.streaming.music.dto.MusicaDTO;
import com.streaming.music.dto.PlaylistComMusicasDTO;
import com.streaming.music.dto.PlaylistDTO;
import com.streaming.music.repository.PlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Transactional(readOnly = true)
    public List<PlaylistDTO> listarPlaylistsPorUsuario(Long usuarioId) {
        return playlistRepository.findByUsuarioId(usuarioId).stream()
            .map(playlist -> new PlaylistDTO(
                playlist.getId(),
                playlist.getNome(),
                playlist.getUsuario().getId(),
                playlist.getUsuario().getNome()
            ))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PlaylistComMusicasDTO listarMusicasDaPlaylist(Long playlistId) {
        return playlistRepository.findById(playlistId)
            .map(playlist -> new PlaylistComMusicasDTO(
                playlist.getId(),
                playlist.getNome(),
                playlist.getMusicas().stream()
                    .map(musica -> new MusicaDTO(
                        musica.getId(),
                        musica.getNome(),
                        musica.getArtista()
                    ))
                    .collect(Collectors.toList())
            ))
            .orElse(null);
    }
}
