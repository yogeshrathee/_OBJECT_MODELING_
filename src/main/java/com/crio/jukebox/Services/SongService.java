package com.crio.jukebox.Services;

import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.Album;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.repositories.IAlbumRepository;
import com.crio.jukebox.repositories.ISongRepository;

public class SongService implements ISongRepository, ISongService {
    private final ISongRepository songRepository;
    private final IAlbumRepository albumRepository;



    public SongService(ISongRepository songRepository2, IAlbumRepository albumRepository) {
        this.songRepository = songRepository2;
        this.albumRepository = albumRepository;
    }

    @Override
    public void addSong(Song song) {
        songRepository.addSong(song);
    }

    @Override
    public Optional<Song> getSong(String songId) {
        return songRepository.getSong(songId);
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepository.getAllSongs();
    }

    @Override
    public List<Song> getSongsByAlbum(Album album) {
        return songRepository.getSongsByAlbum(album);
    }

    @Override
    public Song removeSong(String songId) throws SongNotFoundException {
        return songRepository.removeSong(songId);
    }

    @Override
    public Song getSongById(String songId) {
        return songRepository.getSongById(songId);
    }

    @Override
    public Optional<Song> findByName(String songName) {
        List<Song> allSongs = songRepository.getAllSongs();
        for (Song song : allSongs) {
            if (song.getName().equals(songName)) {
                return Optional.of(song);
            }
        }
        return Optional.empty();
    }



}