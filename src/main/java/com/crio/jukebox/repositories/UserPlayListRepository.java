package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;

public class UserPlayListRepository implements IUserPlayListRepository {
    private final IUserRepository userRepository;
    private final IPlayListRepository playListRepository;

    public UserPlayListRepository(IUserRepository userRepository,
            IPlayListRepository playListRepository) {
        this.userRepository = userRepository;
        this.playListRepository = playListRepository;
    }

    // public UserPlayListRepository() {
    //     this.userRepository = null;
    //     this.playListRepository = null;
    // }

    public UserPlayListRepository() {
        this.userRepository = new UserRepository();
        this.playListRepository = null;
    }

    @Override
    public Integer size() {
        return userRepository.findAll().size();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public PlayList createPlayList(String userId, String playListName)
            throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            PlayList playList = new PlayList(user.getId(), user.getName(), new ArrayList<>());
            user.addPlayList(playList);
            userRepository.saveTwo(user);
            return playList;
        } else {
            throw new IllegalArgumentException("User with id " + userId + " not found.");
        }
    }

    @Override
    public PlayList addSongToPlayList(String userId, String playListName, Song song) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<PlayList> playListOptional = user.getPlayLists().stream()
                    .filter(p -> p.getName().equals(playListName)).findFirst();
            if (playListOptional.isPresent()) {
                PlayList playList = playListOptional.get();
                playListRepository.addSongToPlayList(userOptional.get().getId(), playList.getName(),
                        song.getName());
                playListRepository.addPlaylistToUser(userOptional.get().getId(), playList);
                userRepository.saveTwo(user);
                return playList;
            } else {
                throw new IllegalArgumentException("PlayList with name " + playListName
                        + " not found for user " + user.getName());
            }
        } else {
            throw new IllegalArgumentException("User with id " + userId + " not found.");
        }
    }

    


}