package com.streaming.music.repository;

import com.streaming.music.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("SELECT p FROM Playlist p WHERE p.usuario.id = :usuarioId")
    List<Playlist> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT p FROM Playlist p JOIN p.musicas m WHERE m.id = :musicaId")
    List<Playlist> findByMusicaId(@Param("musicaId") Long musicaId);
}
