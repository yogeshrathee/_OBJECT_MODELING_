package com.crio.jukebox.Services;

import java.util.List;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.SongNotFoundException;

public interface IUserService {
    public PlayList createPlaylist(User user, String playlistName, List<String> songIds)
            throws SongNotFoundException;

    public void deletePlaylist(User user, PlayList playlistName);

    public void addSongToPlaylist(User user, String playlistName, String songId)
            throws SongNotFoundException;

    public User create(String userName);
}