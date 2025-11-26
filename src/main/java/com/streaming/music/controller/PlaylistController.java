package com.streaming.music.controller;

import com.streaming.music.dto.PlaylistComMusicasDTO;
import com.streaming.music.dto.PlaylistDTO;
import com.streaming.music.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PlaylistDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<PlaylistDTO> playlists = playlistService.listarPlaylistsPorUsuario(usuarioId);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{playlistId}/musicas")
    public ResponseEntity<PlaylistComMusicasDTO> listarMusicasDaPlaylist(@PathVariable Long playlistId) {
        PlaylistComMusicasDTO playlist = playlistService.listarMusicasDaPlaylist(playlistId);
        if (playlist != null) {
            return ResponseEntity.ok(playlist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
