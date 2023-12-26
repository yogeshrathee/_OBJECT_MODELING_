package com.crio.jukebox.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.Services.IUserService;
import com.crio.jukebox.Services.UserService;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.repositories.IAlbumRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    private IUserRepository userRepository;
    private IAlbumRepository albumRepository;
    private ISongRepository songRepository;
    private IUserService userService;

    @BeforeEach
    public void setup() {
        userRepository = mock(IUserRepository.class);
        albumRepository = mock(IAlbumRepository.class);
        songRepository = mock(ISongRepository.class);
        userService = new UserService(userRepository, albumRepository, songRepository);
    }

    @Test
    public void testCreatePlaylist() throws SongNotFoundException {
        // Create a user
        User user = new User("1", "John Doe");

        // Create a list of song IDs
        List<String> songIds = new ArrayList<>();
        songIds.add("1");
        songIds.add("2");

        // Create a list of songs
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("1", "Song1"));
        songs.add(new Song("2", "Song2"));

        // Mock the getSongById method of the songRepository to return the songs
        when(songRepository.getSongById("1")).thenReturn(new Song("1", "Song1"));
        when(songRepository.getSongById("2")).thenReturn(new Song("2", "Song2"));

        // Call the createPlaylist method
        PlayList result = userService.createPlaylist(user, "My Playlist", songIds);

        // Verify that the getSongById method of the songRepository is called with the correct song
        // IDs
        verify(songRepository).getSongById("1");
        verify(songRepository).getSongById("2");

        // Verify that the addPlayList method of the userRepository is called with the correct
        // playList and user
        verify(userRepository).addPlayList(result, user);

        // Assert that the returned playlist contains the correct songs
        assertEquals(songs, result.getSongs());
    }

    @Test
    public void testDeletePlaylist() {
        // Create a user
        User user = new User("1", "John Doe");

        // Create a playlist
        PlayList playlist = new PlayList("1", "My Playlist", new ArrayList<>());

        // Call the deletePlaylist method
        userService.deletePlaylist(user, playlist);

        // Verify that the deletePlayList method of the userRepository is called with the correct
        // user and playlist
        verify(userRepository).deletePlayList(user, playlist);
    }

    
}