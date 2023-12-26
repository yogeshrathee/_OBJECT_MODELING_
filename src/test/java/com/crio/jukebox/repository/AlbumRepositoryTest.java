package com.crio.jukebox.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.Album;
import com.crio.jukebox.repositories.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlbumRepositoryTest {

    private AlbumRepository albumRepository;

    @BeforeEach
    public void setUp() {
        albumRepository = new AlbumRepository();
    }

    @Test
    public void testCreateAlbum() {
        Album album = new Album("1", "Album 1", "Arijit Singh");
        albumRepository.createAlbum(album);

        Optional<Album> retrievedAlbum = albumRepository.findAlbumById("1");
        assertTrue(retrievedAlbum.isPresent());
        assertEquals(album, retrievedAlbum.get());
    }

    @Test
    public void testFindAllAlbums() {
        Album album1 = new Album("1", "Album 1", "Arijit Singh");
        Album album2 = new Album("2", "Album 2", "Jubin Nautiyal");
        albumRepository.createAlbum(album1);
        albumRepository.createAlbum(album2);

        List<Album> allAlbums = albumRepository.findAllAlbums();
        List<Album> expectedAlbums = new ArrayList<>();
        expectedAlbums.add(album1);
        expectedAlbums.add(album2);

        assertEquals(expectedAlbums, allAlbums);
    }

    @Test
    public void testUpdateAlbum() {
        Album album = new Album("1", "Album 1", "Arijit Singh");
        albumRepository.createAlbum(album);

        Album updatedAlbum = new Album("1", "Updated Album 1", "Arijit Singh");
        albumRepository.updateAlbum(updatedAlbum);

        Optional<Album> retrievedAlbum = albumRepository.findAlbumById("1");
        assertTrue(retrievedAlbum.isPresent());
        assertEquals(updatedAlbum, retrievedAlbum.get());
    }

    @Test
    public void testDeleteAlbum() {
        Album album1 = new Album("1", "Album 1", "Arijit Singh");
        albumRepository.createAlbum(album1);

        albumRepository.deleteAlbum("1");
        Optional<Album> retrievedAlbum = albumRepository.findAlbumById("1");
        assertFalse(retrievedAlbum.isPresent());
    }
}