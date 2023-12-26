package com.crio.jukebox.repository;


// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.entities.*;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.PlayListRepository;
import com.crio.jukebox.repositories.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayListRepositoryTest {
    private PlayListRepository playListRepository;
    private ISongRepository songRepository;
    private User user;


    @BeforeEach
    public void setup() {
        Map<String, Song> songs = new HashMap<>();
        songRepository = new SongRepository(songs);
        user = new User("1", "John Doe");
        playListRepository = new PlayListRepository(songRepository, user);
    }

    @Test
    public void testAddPlaylistToUser() {
        // Create a new user
        User user = new User("2", "Tanuj");

        // Create a new instance of PlayListRepository with the user
        PlayListRepository playListRepository = new PlayListRepository(songRepository, user);

        // Define the playlist to add
        PlayList playlist = new PlayList("2", "My Playlist", new ArrayList<>());

        // Assert that UserNotFoundException is thrown when adding the playlist
        assertThrows(UserNotFoundException.class,
                () -> playListRepository.addPlaylistToUser("3", playlist));

        // Add the playlist to the user
        try {
            playListRepository.addPlaylistToUser(user.getId(), playlist);

            // Retrieve all playlists for the user
            List<PlayList> playlists = playListRepository.getAllPlayLists(user.getId());
            assertEquals(1, playlists.size());
            assertEquals(playlist.getId(), playlists.get(0).getId());
            assertEquals(playlist.getName(), playlists.get(0).getName());
            assertEquals(playlist.getSongs(), playlists.get(0).getSongs());
        } catch (UserNotFoundException e) {

        }
    }


    

    

}