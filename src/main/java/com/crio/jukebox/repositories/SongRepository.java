package com.crio.jukebox.repositories;

import java.util.ArrayList;
// import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.Album;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.SongNotFoundException;

public class SongRepository implements ISongRepository {
    private final Map<String, Song> songs;

    public SongRepository(Map<String, Song> songs2) {
        this.songs =songs2;
    }

    public SongRepository() {
        this.songs= null;
    }

    @Override
    public void addSong(Song song) {
        this.songs.put(song.getId(), song);
    }

    @Override
    public Optional<Song> getSong(String songId) {
        return Optional.ofNullable(this.songs.get(songId));
    }

    @Override
    public List<Song> getAllSongs() {
        return new ArrayList<>(this.songs.values());
    }

    @Override
    public List<Song> getSongsByAlbum(Album album) {
        List<Song> result = new ArrayList<>();
        for (Song song : this.songs.values()) {
            if (song.getAlbum().equals(album)) {
                result.add(song);
            }
        }
        return result;
    }
    

    @Override
    public Song removeSong(String songId) throws SongNotFoundException {
        Song song = this.songs.remove(songId);
        if (song == null) {
            throw new SongNotFoundException("Song not found with ID: " + songId);
        }
        return song;
    }

    @Override
    public Song getSongById(String songId) {
        return songs.get(songId);
    }

    @Override
    public Optional<Song> findByName(String songName) {
        // TODO Auto-generated method stub
        return songs.values().stream().filter(u -> u.getName().equals(songName)).findAny();
    }

   

}