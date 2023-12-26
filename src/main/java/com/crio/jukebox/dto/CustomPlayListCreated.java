package com.crio.jukebox.dto;

import java.util.List;
import com.crio.jukebox.entities.Song;

public class CustomPlayListCreated {
    private final String playlistId;
    private final String playlistName;
    private final List<Song> songIds;

    public CustomPlayListCreated(String playlistId, String playlistName, List<Song> list) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.songIds = list;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public List<Song> getSongIds() {
        return songIds;
    }
}