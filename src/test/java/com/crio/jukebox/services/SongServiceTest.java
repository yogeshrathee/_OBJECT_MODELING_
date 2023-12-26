package com.crio.jukebox.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.Services.SongService;
import com.crio.jukebox.entities.Album;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.IAlbumRepository;
import com.crio.jukebox.repositories.ISongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SongServiceTest {
    private ISongRepository songRepository;
    private IAlbumRepository albumRepository;
    private SongService songService;

    @BeforeEach
    public void setup() {
        songRepository = mock(ISongRepository.class);
        albumRepository = mock(IAlbumRepository.class);
        songService = new SongService(songRepository, albumRepository);
    }

    @Test
    public void testAddSong() {
        // Create a song
        Song song = new Song("1", "Song1");

        // Call the addSong method
        songService.addSong(song);

        // Verify that the addSong method of the songRepository is called with the correct song
        verify(songRepository).addSong(song);
    }

    @Test
    public void testGetSong() {
        // Create a song with ID "1"
        Song song = new Song("1", "Song1");

        // Mock the getSong method of the songRepository to return the song
        when(songRepository.getSong("1")).thenReturn(Optional.of(song));

        // Call the getSong method
        Optional<Song> result = songService.getSong("1");

        // Verify that the getSong method of the songRepository is called with the correct ID
        verify(songRepository).getSong("1");

        // Assert that the returned song is the same as the one we mocked
        assertTrue(result.isPresent());
        assertEquals(song, result.get());
    }

    @Test
    public void testGetAllSongs() {
        // Create a list of songs
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("1", "Song1"));
        songs.add(new Song("2", "Song2"));

        // Mock the getAllSongs method of the songRepository to return the list of songs
        when(songRepository.getAllSongs()).thenReturn(songs);

        // Call the getAllSongs method
        List<Song> result = songService.getAllSongs();

        // Verify that the getAllSongs method of the songRepository is called
        verify(songRepository).getAllSongs();

        // Assert that the returned list of songs is the same as the one we mocked
        assertEquals(songs, result);
    }

    @Test
    public void testGetSongsByAlbum() {
        // Create an album
        Album album = new Album("1", "Album1", "Tanuj Arora");

        // Create a list of songs
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("1", "Song1"));
        songs.add(new Song("2", "Song2"));

        // Mock the getSongsByAlbum method of the songRepository to return the list of songs
        when(songRepository.getSongsByAlbum(album)).thenReturn(songs);

        // Call the getSongsByAlbum method
        List<Song> result = songService.getSongsByAlbum(album);

        // Verify that the getSongsByAlbum method of the songRepository is called with the correct
        // album
        verify(songRepository).getSongsByAlbum(album);

        // Assert that the returned list of songs is the same as the one we mocked
        assertEquals(songs, result);
    }

}