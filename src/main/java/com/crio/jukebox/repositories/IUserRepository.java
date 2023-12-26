package com.crio.jukebox.repositories;

import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.PlayListNotFoundException;

public interface IUserRepository {
    public Optional<User> findByName(String name);
    public void addPlayList(PlayList playList,User user);
    public User save(Optional<User> user);
    public void deletePlayList(User user, PlayList playlist) throws PlayListNotFoundException;
    public List<PlayList> getPlayLists(User user);
    public List<User> findAll();
    public void setCurrentPlayList(PlayList playList, User user);
    public Optional<User> findById(String id);
    public PlayList getCurrentPlayList(User user);
    public boolean existsById(String id);

    public void delete(User entity);

    public void deleteById(String id);

    public long count();
    public User saveTwo(User entity);


}