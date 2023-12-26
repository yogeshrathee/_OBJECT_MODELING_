package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Song extends BaseEntity {
    private String id;
    private String name;
    private List<String> artists;
    private final String leadArtist;
    private Album album;

    public String generateRandomString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public Song(String id, String name, List<String> artists, Album album, String leadArtist) {
        this.id = id;
        this.name = name;
        this.artists = artists;
        this.album = album;
        this.leadArtist = leadArtist;

    }
    public Song(String id, String name, Album album, String leadArtist) {
        this.id = id;
        this.name = name;
        this.artists = new ArrayList<>();
        this.album = album;
        this.leadArtist = leadArtist;

    }

    public Song(String id, String songName) {
        this.id = id;
        this.name = songName;
        this.artists = new ArrayList<>();
        this.album = null;
        this.leadArtist = null;
    }

    public Song(String songName) {
        this.id = generateRandomString();
        this.name = songName;
        this.leadArtist = null;
        this.artists = new ArrayList<>();
        this.album = null;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getArtists() {
        return artists;
    }

    public Album getAlbum() {
        return album;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((leadArtist == null) ? 0 : leadArtist.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Song other = (Song) obj;
        if (leadArtist == null) {
            if (other.leadArtist != null)
                return false;
        } else if (!leadArtist.equals(other.leadArtist))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}