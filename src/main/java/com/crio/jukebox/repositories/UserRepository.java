package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.PlayListNotFoundException;

public class UserRepository implements IUserRepository {
    private final Map<String, User> userMap;
    private final IUserPlayListRepository userPlayListRepository;
    private final ISongRepository songRepository;
    private int autoIncrement;

    public UserRepository() {
        userMap = new HashMap<>();
        userPlayListRepository = null;
        autoIncrement = 0;
        songRepository = null;
    }

    public UserRepository(IUserPlayListRepository userPlayListRepository,
            ISongRepository songRepository) {
        this.userPlayListRepository = userPlayListRepository;
        this.songRepository = songRepository;
        this.userMap = new HashMap<>();
        autoIncrement = 0;

        // Get all users from the userPlayListRepository and populate the userMap
        List<User> allUsers = userPlayListRepository.getAllUsers();
        for (User user : allUsers) {
            int id = Integer.parseInt(user.getId());
            if (id > autoIncrement) {
                autoIncrement = id;
            }
            userMap.put(user.getId(), user);
        }
    }

    @Override
    public User saveTwo(User entity) {
        if (entity.getId() == null) {
            autoIncrement++;
            User u = new User(Integer.toString(autoIncrement), entity.getName());
            userMap.put(u.getId(), u);
            return u;
        }
        userMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return userMap.containsKey(id);
    }

    @Override
    public void delete(User entity) {
        userMap.remove(entity.getId());
    }

    @Override
    public void deleteById(String id) {
        userMap.remove(id);
    }

    @Override
    public long count() {
        return userMap.size();
    }

    @Override
    public Optional<User> findByName(String name) {
        return userMap.values().stream().filter(u -> u.getName().equals(name)).findAny();
    }

    @Override
    public void addPlayList(PlayList playList, User user) {
        List<PlayList> allPlayList = user.getPlayLists();
        allPlayList.add(playList);
    }

    @Override
    public void deletePlayList(User user, PlayList playlist) throws PlayListNotFoundException {
        List<PlayList> allPlayLists = user.getPlayLists();
        boolean playlistFound = false;

        for (PlayList p : allPlayLists) {
            if (p.getName().equals(playlist.getName())) {
                allPlayLists.remove(p);
                playlistFound = true;
                break;
            }
        }

        if (!playlistFound) {
            throw new PlayListNotFoundException("Playlist not found: " + playlist.getName());
        }
    }

    @Override
    public List<PlayList> getPlayLists(User user) {
        return user.getPlayLists();
    }

    @Override
    public void setCurrentPlayList(PlayList playList, User user) {
        user.setCurrentPlayList(playList);
    }

    @Override
    public PlayList getCurrentPlayList(User user) {
        return user.getCurrentPlaylist();
    }

    @Override
    public User save(Optional<User> user) {

        if (user.get().getId() == null) {
            autoIncrement++;
            User u = new User(Integer.toString(autoIncrement), user.get().getName());
            userMap.put(u.getId(), u);
            return u;
        }
        userMap.put(user.get().getId(), user.get());
        return user.get();
    }



}