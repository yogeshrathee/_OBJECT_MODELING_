package com.crio.jukebox.dto;

import com.crio.jukebox.entities.SongPlayingOrder;

public class UserPlayedSongDto {
    private final String username;
    private final String songname;
    private final String albumname;
    private final String artistname;
    private final SongPlayingOrder order;
    private final Integer indexOfSongs;

    public UserPlayedSongDto(String username, String songname, String albumname,
            String artistname) {
        this.username = username;
        this.songname = songname;
        this.albumname = albumname;
        this.artistname = artistname;
        this.order = null;
        this.indexOfSongs = 0;
    }

    public UserPlayedSongDto(String username, SongPlayingOrder order) {
        this.username = username;
        this.songname = null;
        this.albumname = null;
        this.artistname = null;
        this.order = order;
        this.indexOfSongs = 0;
    }

    public UserPlayedSongDto(String userName, String songName, String artistName, int indexOf) {
        this.username = userName;
        this.songname = songName;
        this.albumname = null;
        this.artistname = artistName;
        this.order = null;
        this.indexOfSongs = 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((albumname == null) ? 0 : albumname.hashCode());
        result = prime * result + ((artistname == null) ? 0 : artistname.hashCode());
        result = prime * result + ((songname == null) ? 0 : songname.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserPlayedSongDto other = (UserPlayedSongDto) obj;
        if (albumname == null) {
            if (other.albumname != null)
                return false;
        } else if (!albumname.equals(other.albumname))
            return false;
        if (artistname == null) {
            if (other.artistname != null)
                return false;
        } else if (!artistname.equals(other.artistname))
            return false;
        if (songname == null) {
            if (other.songname != null)
                return false;
        } else if (!songname.equals(other.songname))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Current Song Playing [albumname=" + albumname + ", artistname=" + artistname
                + ", songname=" + songname + "]";
    }


}