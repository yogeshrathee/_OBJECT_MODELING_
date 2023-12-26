package com.crio.jukebox.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
// import com.crio.jukebox.Services.IUserService;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.repositories.IAlbumRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;
// import com.crio.jukebox.repositories.UserPlayListRepository;

public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IAlbumRepository albumRepository;
    private final ISongRepository songRepository;

    public UserService(IUserRepository userRepository, IAlbumRepository albumRepository,
            ISongRepository songRepository) {
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    @Override
    public PlayList createPlaylist(User user, String playlistName, List<String> songIds)
            throws SongNotFoundException {
        List<Song> songs = new ArrayList<>();
        for (String songId : songIds) {
            Song song = songRepository.getSongById(songId);
            if (song == null) {
                throw new SongNotFoundException("Song not found with ID: " + songId);
            }
            songs.add(song);
        }
        PlayList playList = new PlayList("1", "PlayList 1", songs);
        userRepository.addPlayList(playList, user);
        return playList;
    }

    @Override
    public void deletePlaylist(User user, PlayList playlistName) {
        userRepository.deletePlayList(user, playlistName);
    }

    @Override
    public void addSongToPlaylist(User user, String playlistName, String songId)
            throws SongNotFoundException {
        List<PlayList> userPlayLists = userRepository.getPlayLists(user);

        PlayList playList = null;
        for (PlayList playlist : userPlayLists) {
            if (playlist.getName().equals(playlistName)) {
                playList = playlist;
                break;
            }
        }

        if (playList == null) {
            throw new SongNotFoundException("Playlist not found with name: " + playlistName);
        }

        Song song = songRepository.getSongById(songId);
        if (song == null) {
            throw new SongNotFoundException("Song not found with ID: " + songId);
        }

        playList.addSong(song);
        userRepository.saveTwo(user);
    }

    @Override
    public User create(String userName) {
        String userId = UUID.randomUUID().toString();
        User newUser = new User(userId, userName);
        userRepository.saveTwo(newUser);
        return newUser;
    }
}