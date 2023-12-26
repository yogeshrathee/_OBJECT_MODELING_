package com.crio.jukebox.repositories;

import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.Album;

public interface IAlbumRepository {
    void createAlbum(Album album);

    Optional<Album> findAlbumById(String albumId);

    List<Album> findAllAlbums();

    void updateAlbum(Album album);

    void deleteAlbum(String albumId);
}