package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.*;
import com.crio.jukebox.exceptions.*;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.PlayListNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;

public class PlayListRepository implements IPlayListRepository {
    private final Map<String, List<PlayList>> playlistsByUser;
    private final ISongRepository songRepository;
    private final User user;
    private PlayListRepository playListRepository;
    private UserRepository userRepository;

    public PlayListRepository(ISongRepository songRepository, User user) {
        this.playlistsByUser = new HashMap<>();
        this.songRepository = songRepository;
        this.user = user;
    }

    public PlayListRepository(ISongRepository songRepository) {
        this.playlistsByUser = new HashMap<>();
        this.songRepository = songRepository;
        this.user = null;
    }


    @Override
    public void addPlaylistToUser(String userId, PlayList playlist) throws UserNotFoundException {
        List<PlayList> playlists = playlistsByUser.get(userId);
        if (playlists == null) {
            throw new UserNotFoundException("User not found: " + userId);
        }
        playlists.add(playlist);
        playlistsByUser.put(userId, playlists);
    }


    @Override
    public List<PlayList> getAllPlayLists(String userId) throws UserNotFoundException {
        List<PlayList> playlists = playlistsByUser.get(userId);
        if (playlists == null) {
            throw new UserNotFoundException("User not found: " + userId);
        }
        return new ArrayList<>(playlists);
    }

    @Override
    public Optional<PlayList> getPlayList(String userId, String playListName)
            throws UserNotFoundException {
        List<PlayList> playlists = playlistsByUser.get(userId);
        if (playlists == null) {
            throw new UserNotFoundException("User not found: " + userId);
        }
        return playlists.stream().filter(playlist -> playlist.getName().equals(playListName))
                .findFirst();
    }

    @Override
    public PlayList createPlayList(String userId, String playListName, List<String> songIds)
            throws UserNotFoundException, SongNotFoundException {
        List<PlayList> playlists = playlistsByUser.get(userId);
        if (playlists == null) {
            throw new UserNotFoundException("User not found: " + userId);
        }

        List<Song> songs = new ArrayList<>();
        for (String songId : songIds) {
            // Assume there is a SongRepository that provides the songs by ID
            // and throws a SongNotFoundException if the song is not found.
            Song song = songRepository.getSongById(songId);
            if (song == null) {
                throw new SongNotFoundException("Song not found: " + songId);
            }
            songs.add(song);
        }

        PlayList playlist = new PlayList(null, playListName, songs);
        playlists.add(playlist);
        return playlist;
    }

    @Override
    public PlayList addSongToPlayList(String userId, String playListName, String songId)
            throws UserNotFoundException, SongNotFoundException {
        Optional<PlayList> playlistOpt = getPlayList(userId, playListName);
        if (!playlistOpt.isPresent()) {
            throw new IllegalArgumentException("Playlist not found: " + playListName);
        }

        PlayList playlist = playlistOpt.get();
        Song song = songRepository.getSongById(songId);
        if (song == null) {
            throw new SongNotFoundException("Song not found: " + songId);
        }

        List<Song> songs = new ArrayList<>(playlist.getSongs());
        songs.add(song);

        PlayList updatedPlaylist = new PlayList(playlist.getId(), playlist.getName(), songs);
        playlistsByUser.get(userId).remove(playlist);
        playlistsByUser.get(userId).add(updatedPlaylist);

        return updatedPlaylist;
    }

    public PlayList removeSongFromPlayList(String userId, String playListName, String songId)
            throws UserNotFoundException, SongNotFoundException {
        Optional<PlayList> playlistOpt = getPlayList(userId, playListName);
        if (!playlistOpt.isPresent()) {
            throw new IllegalArgumentException("Playlist not found: " + playListName);
        }
        PlayList playlist = playlistOpt.get();

        Optional<Song> songOpt = playlist.getSongs().stream()
                .filter(song -> song.getId().equals(songId)).findFirst();

        if (!songOpt.isPresent()) {
            throw new SongNotFoundException("Song not found in the playlist: " + songId);
        }

        // Remove the song from the playlist and return the modified playlist
        playlist.getSongs().remove(songOpt.get());
        return playlist;
    }


    @Override
    public void addSong(String playlistName, Song song, com.crio.jukebox.entities.User user) {
        List<PlayList> playLists = getAllPlayLists(user.getId());
        for (PlayList playList : playLists) {
            if (playList.getName().equals(playlistName)) {
                playList.addSong(song);
                break;

            }
        }
    }

    @Override
    public void removePlayList(String playlistName, com.crio.jukebox.entities.User user) {
        List<PlayList> aList = user.getPlayLists();
        for (PlayList play : aList) {
            if (play.getName().equals(playlistName)) {
                aList.remove(play);
                break; // to exit the loop once the playlist is removed
            }
        }

    }


    @Override
    public void removePlayListWithoutUserId(String playListname, String userId)
            throws PlayListNotFoundException {
        List<PlayList> allPlaylists = playListRepository.getAllPlayLists(userId);
        Iterator<PlayList> iterator = allPlaylists.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            PlayList playlist = iterator.next();
            if (playlist.getName().equals(playListname)) {
                iterator.remove();
                found = true;
            }
        }
        if (!found) {
            throw new PlayListNotFoundException(
                    "No playlist found with name " + playListname + " for user " + userId);
        }
    }

    @Override
    public void updatePlayList(String userId, PlayList existingPlayList, Song song) {
        // Find the user with the given userId
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Update the user's playlist with the new song
            user.getCurrentPlaylist().addSong(song);
            // Save the updated user object to the database
            userRepository.saveTwo(user);
        } else {
            // Handle the case where no user with the given userId was found
            throw new IllegalArgumentException("User not found for id: " + userId);
        }
    }
}