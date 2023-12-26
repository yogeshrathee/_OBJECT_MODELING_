package com.crio.jukebox.dto;

import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;

public class NewSongAddedToPlayList {
    private Song song;
    private PlayList playList;

    public NewSongAddedToPlayList(Song song, PlayList playList) {
        this.song = song;
        this.playList = playList;
    }

    public Song getSong() {
        return song;
    }

    public PlayList getPlayList() {
        return playList;
    }
}