package com.crio.jukebox.entities;

import java.util.List;

public class Album extends BaseEntity {

    private String name;
    private String artist;

    private String id;
    public Album(String id, String name, String artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }
}
    