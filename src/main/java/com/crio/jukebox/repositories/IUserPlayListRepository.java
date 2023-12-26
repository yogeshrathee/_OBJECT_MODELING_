package com.crio.jukebox.repositories;

import java.util.List;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;

public interface IUserPlayListRepository {

    Integer size();
    List<User> getAllUsers();
    public PlayList createPlayList(String userId, String playListName);
    public PlayList addSongToPlayList(String userId, String playListName, Song song);
    
}