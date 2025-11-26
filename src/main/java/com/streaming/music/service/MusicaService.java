package com.streaming.music.service;

import com.streaming.music.dto.MusicaDTO;
import com.streaming.music.model.Musica;
import com.streaming.music.model.Playlist;
import com.streaming.music.repository.MusicaRepository;
import com.streaming.music.repository.PlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicaService {

    private final MusicaRepository musicaRepository;
    private final PlaylistRepository playlistRepository;

    public MusicaService(MusicaRepository musicaRepository, PlaylistRepository playlistRepository) {
        this.musicaRepository = musicaRepository;
        this.playlistRepository = playlistRepository;
    }

    @Transactional(readOnly = true)
    public List<MusicaDTO> listarTodas() {
        return musicaRepository.findAll().stream()
            .map(musica -> new MusicaDTO(
                musica.getId(),
                musica.getNome(),
                musica.getArtista()
            ))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MusicaDTO buscarPorId(Long id) {
        return musicaRepository.findById(id)
            .map(musica -> new MusicaDTO(
                musica.getId(),
                musica.getNome(),
                musica.getArtista()
            ))
            .orElse(null);
    }

    @Transactional
    public Musica criar(String nome, String artista) {
        Musica musica = new Musica(nome, artista);
        return musicaRepository.save(musica);
    }

    @Transactional
    public Musica atualizar(Long id, String nome, String artista) {
        Musica musica = musicaRepository.findById(id).orElse(null);
        if (musica == null) {
            return null;
        }
        musica.setNome(nome);
        musica.setArtista(artista);
        return musicaRepository.save(musica);
    }

    @Transactional
    public boolean deletar(Long id) {
        Musica musica = musicaRepository.findById(id).orElse(null);
        if (musica == null) {
            return false;
        }

        List<Playlist> playlists = playlistRepository.findByMusicaId(id);
        for (Playlist playlist : playlists) {
            playlist.getMusicas().removeIf(m -> m.getId().equals(id));
            playlistRepository.save(playlist);
        }

        musicaRepository.delete(musica);
        return true;
    }
}
