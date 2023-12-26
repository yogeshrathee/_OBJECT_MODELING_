package com.crio.jukebox.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crio.jukebox.entities.User;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.repositories.IPlayListRepository;
import com.crio.jukebox.dto.CustomPlayListCreated;
import com.crio.jukebox.dto.UserPlayedSongDto;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.SongPlayingOrder;
import com.crio.jukebox.exceptions.InvalidPlayListOperationException;
import com.crio.jukebox.exceptions.InvalidSongOperationException;
import com.crio.jukebox.exceptions.PlayListNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.ISongRepository;



public class UserPlayListService implements IUserPlayListService {
    private final IUserRepository userRepository;
    private final IPlayListRepository playListRepository;
    private final ISongRepository songRepository;

    public UserPlayListService(IUserRepository userRepository,
            IPlayListRepository playListRepository, ISongRepository songRepository) {
        this.userRepository = userRepository;
        this.playListRepository = playListRepository;
        this.songRepository = songRepository;
    }

   

    @Override
    public PlayList deleteSongFromPlayList(String userId, String playListName, String songName) {
        Optional<User> user = userRepository.findById(userId);
        PlayList currentPlayList = user.get().getCurrentPlaylist();
        List<Song> allSongs = currentPlayList.getSongs();
        Optional<Song> songToDelete =
                allSongs.stream().filter(song -> song.getName().equals(songName)).findFirst();
        if (songToDelete.isPresent()) {
            allSongs.remove(songToDelete.get());
            return currentPlayList;
        } else {
            throw new SongNotFoundException("Song not found in playlist");
        }
    }

    @Override
    public PlayList addSongToPlayListByList(String userId, PlayList playList,
            List<Song> songsToAdd) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPlayLists().contains(playList)) {
                PlayList updatedPlayList = user.getPlayLists().stream()
                        .filter(pl -> pl.equals(playList)).findFirst().get();
                updatedPlayList.getSongs().addAll(songsToAdd);
                userRepository.saveTwo(user);
                return updatedPlayList;
            }
        }
        return null;
    }

    @Override
    public PlayList addSongToPlayListByName(String userId, String playlistName, String song) {
        Optional<Song> songtwo = songRepository.findByName(song);
        if (!songtwo.isPresent()) {
            // Song not found, throw an exception or return null
            throw new SongNotFoundException("Song not found: " + song);
        }

        // Find the user's playlist
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            // User not found, throw an exception or return null
            throw new UserNotFoundException("User not found: " + userId);
        }
        User user = optionalUser.get();
        PlayList userPlayList = user.getCurrentPlaylist();
        Song newSong = new Song(userId, song);
        // Add the song to the playlist
        userPlayList.addSong(newSong);

        // Update the user's current playlist
        userRepository.saveTwo(user);

        return userPlayList;
    }


    @Override
    public UserPlayedSongDto setCurrentPlayList(String userId, String playListName) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        User user = optionalUser.get();
        Optional<PlayList> optionalPlayList = user.getPlayLists().stream()
                .filter(p -> p.getName().equals(playListName)).findAny();
        if (!optionalPlayList.isPresent()) {
            throw new PlayListNotFoundException(
                    "Playlist " + playListName + " not found for user " + user.getName());
        }
        PlayList playList = optionalPlayList.get();
        user.setCurrentPlayList(playList);
        return new UserPlayedSongDto(user.getName(), playList.getName(), null, null);
    }

    @Override
    public UserPlayedSongDto playSongByName(String userId, String name) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        User user = optionalUser.get();
        PlayList currentPlayList = user.getCurrentPlaylist();
        if (currentPlayList == null) {
            throw new InvalidPlayListOperationException(
                    "User " + user.getName() + " does not have a current playlist set");
        }
        Optional<Song> optionalSong = songRepository.findByName(name);
        if (!optionalSong.isPresent()) {
            throw new SongNotFoundException("Song with name " + name + " not found");
        }
        Song song = optionalSong.get();
        if (!currentPlayList.getSongs().contains(song)) {
            throw new SongNotFoundException(
                    "Song with name " + name + " not found in the current playlist");
        }
        return new UserPlayedSongDto(user.getName(), currentPlayList.getName(), song.getName(),
                currentPlayList.getSongs().indexOf(song));
    }

    public UserPlayedSongDto playSongByOrder(String userId, SongPlayingOrder order) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        User user = optionalUser.get();
        // PlayList currentPlaylist = user.getCurrentPlaylist();
        Song songToPlay = null;

        switch (order) {
            case NEXT:
                songToPlay = user.getNextSong();
                break;
            case BACK:
                songToPlay = user.getPreviousSong();
                break;
            default:
                throw new IllegalArgumentException("Invalid SongPlayingOrder: " + order);
        }

        if (songToPlay == null) {
            throw new InvalidSongOperationException("No songs in the user's queue to play");
        }

        user.playSong(songToPlay);
        userRepository.saveTwo(user);

        return new UserPlayedSongDto(user.getId(), songToPlay.getId(), songToPlay.getName(),
                songToPlay.getArtists().get(0));
    }

    public CustomPlayListCreated createPlayList(String userId, String name,
            List<Song> playListSongs) {
        PlayList newPlayList;
        if (playListSongs.isEmpty()) {
            newPlayList = new PlayList(name, new ArrayList<>());
        } else {
            newPlayList = new PlayList(name, playListSongs);
        }
        List<User> users = userRepository.findAll();
        User newuser = null;
        for (User u : users) {
            if (u.getId().equals(userId)) {
                newuser = u;
                break;
            }
        }
        List<String> songIds = new ArrayList<>();
        for (Song song : playListSongs) {
            songIds.add(song.getId());
        }
        playListRepository.createPlayList(userId, newPlayList.toString(), songIds);
        if (newuser != null) {
            userRepository.saveTwo(newuser);
        }
        return new CustomPlayListCreated(newPlayList.getId(), newPlayList.getName(),
                newPlayList.getSongs());
    }

    @Override
    public PlayList addSongToPlayList(String userId, String playListName, List<String> csong) {
        Optional<User> user = userRepository.findById(userId);
        List<PlayList> allPlaylists = user.get().getPlayLists();
        for (PlayList playlist : allPlaylists) {
            if (playlist.getName().equals(playListName)) {
                List<Song> songsToAdd = new ArrayList<>();
                for (String songName : csong) {
                    Optional<Song> song = songRepository.findByName(songName);
                    if (song.isPresent()) {
                        songsToAdd.add(song.get());
                    }
                }
                playlist.addSongByList(songsToAdd);
                return playlist;
            }
        }
        return null;
    }

    @Override
    public PlayList addSongToPlayListByNameString(String userId, PlayList playlist, String song) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PlayList deleteSongListFromPlayList(String userId, String playListName,
            List<String> songsNameList) {
        Optional<User> user = userRepository.findById(userId);
        List<PlayList> userPlayList = user.get().getPlayLists();
        Optional<PlayList> playList =
                userPlayList.stream().filter(pl -> pl.getName().equals(playListName)).findFirst();
        if (playList.isPresent()) {
            PlayList playlist = playList.get();
            List<Song> songs = playlist.getSongs();
            songs.removeIf(song -> songsNameList.contains(song.getName()));
            return playlist;
        } else {
            // Handle the case where the playlist is not found
            return null;
        }
    }



    // constructor that initializes the repositories


}