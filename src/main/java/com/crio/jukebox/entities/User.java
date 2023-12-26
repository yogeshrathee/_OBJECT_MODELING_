package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class User extends BaseEntity {
    private String id;
    private String name;
    private List<PlayList> playLists = new ArrayList<>();
    private HashMap<String, PlayList> map = new HashMap<>();
    private Queue<Song> songQueue = new LinkedList<>();

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Song getPreviousSong() {
        if (songQueue.isEmpty()) {
            return null;
        }
        // Get the current song and remove it from the queue
        Song currentSong = songQueue.poll();
        // Add the current song back to the end of the queue
        songQueue.offer(currentSong);
        // Get the previous song, which is now at the front of the queue
        Song previousSong = songQueue.peek();
        // Add the current song back to the front of the queue
        songQueue.offer(currentSong);
        return previousSong;
    }

    public Song getNextSong() {
        if (songQueue.isEmpty()) {
            return null;
        }
        // Get the next song, which is at the front of the queue
        return songQueue.peek();
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PlayList> getPlayLists() {
        return playLists;
    }

    public PlayList getCurrentPlaylist() {
        if (playLists.isEmpty()) {
            // If the user does not have any playlists, create a new one
            PlayList newPlaylist = new PlayList("New PlayList", new ArrayList<>());
            playLists.add(newPlaylist);
            return newPlaylist;
        } else {
            // Otherwise, return the last playlist in the list
            return playLists.get(playLists.size() - 1);
        }
    }


    public void addPlayList(PlayList playList) {
        playLists.add(playList);
        map.put(playList.getName(), playList);
    }

    public void deletePlayList(PlayList playlist) {
        playLists.remove(playlist);
        map.remove(playlist.getName());
    }

    public void removePlayList(String playlistName) {
        playLists.removeIf(playlist -> playlist.getName().equals(playlistName));
        map.remove(playlistName);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }

    public void setCurrentPlayList(PlayList playList) {
        playLists.add(playList);
    }

    public void playSong(Song songToPlay) {
        System.out.println("Playing Current Song" + songToPlay.getName() + "by"
                + songToPlay.getArtists().toString());
    }
}