package com.crio.jukebox.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.Album;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.repositories.SongRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SongRepositoryTest {
    private SongRepository songRepository;
    private Album album1;
    private Album album2;

    @BeforeEach
    public void setup() {
        Map<String, Song> songs = new HashMap<>();
        album1 = new Album("1", "Album 1", "Arjit Singh");
        album2 = new Album("2", "Album 2", "Shreya Ghoshal");

        Song song1 = new Song("1", "Song 1", album1, "Arjit Singh");
        Song song2 = new Song("2", "Song 2", album2, "Shreya Ghoshal");
        Song song3 = new Song("3", "Song 3", album1, "Arjit Singh");

        songs.put(song1.getId(), song1);
        songs.put(song2.getId(), song2);
        songs.put(song3.getId(), song3);

        songRepository = new SongRepository(songs);
    }

    @Test
    public void testAddSong() {
        Song song3 = new Song("3", "Song 3");
        songRepository.addSong(song3);
        Optional<Song> retrievedSong = songRepository.getSong("3");
        Assertions.assertTrue(retrievedSong.isPresent());
        Assertions.assertEquals(song3, retrievedSong.get());
    }

    @Test
    public void testGetSong() {
        Optional<Song> retrievedSong = songRepository.getSong("1");
        Assertions.assertTrue(retrievedSong.isPresent());
        Assertions.assertEquals("Song 1", retrievedSong.get().getName());
    }

    @Test
    public void testGetAllSongs() {
        List<Song> allSongs = songRepository.getAllSongs();
        Assertions.assertEquals(3, allSongs.size());
    }

    @Test
    public void testGetSongsByAlbum() {
        List<Song> songsByAlbum1 = songRepository.getSongsByAlbum(album1);
        assertEquals(2, songsByAlbum1.size());
        assertEquals("Song 1", songsByAlbum1.get(0).getName());
        assertEquals("Song 3", songsByAlbum1.get(1).getName());
    }

    @Test
    public void testRemoveSong() throws SongNotFoundException {
        Song removedSong = songRepository.removeSong("1");
        Assertions.assertEquals("Song 1", removedSong.getName());
        Assertions.assertEquals(2, songRepository.getAllSongs().size());
    }

    @Test
    public void testRemoveSongThrowsException() {
        Assertions.assertThrows(SongNotFoundException.class, () -> {
            songRepository.removeSong("5");
        });
    }

    @Test
    public void testGetSongById() {
        Song song = songRepository.getSongById("2");
        Assertions.assertNotNull(song);
        Assertions.assertEquals("Song 2", song.getName());
    }

    @Test
    public void testFindByName() {
        Optional<Song> foundSong = songRepository.findByName("Song 1");
        Assertions.assertTrue(foundSong.isPresent());
        Assertions.assertEquals("1", foundSong.get().getId());
    }
}