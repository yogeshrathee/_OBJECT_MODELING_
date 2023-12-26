package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.Album;


public class AlbumRepository implements IAlbumRepository {
    private final Map<String, Album> albumMap;

    public AlbumRepository() {
        this.albumMap = new HashMap<>();
    }

    @Override
    public void createAlbum(Album album) {
        albumMap.put(album.getId(), album);
    }

    @Override
    public Optional<Album> findAlbumById(String albumId) {
        return Optional.ofNullable(albumMap.get(albumId));
    }

    @Override
    public List<Album> findAllAlbums() {
        return new ArrayList<>(albumMap.values());
    }

    @Override
    public void updateAlbum(Album album) {
        albumMap.put(album.getId(), album);
    }

    @Override
    public void deleteAlbum(String albumId) {
        albumMap.remove(albumId);
    }
}