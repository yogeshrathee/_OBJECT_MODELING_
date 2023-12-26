package com.crio.jukebox.repositories;

import java.util.List;
import java.util.Optional;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.SongNotFoundException;

public interface IPlayListRepository {
        List<PlayList> getAllPlayLists(String userId) throws UserNotFoundException;

        public void removePlayList(String playlistName, User user);

        public void removePlayListWithoutUserId(String playListname, String userId);

        Optional<PlayList> getPlayList(String userId, String playListName)
                        throws UserNotFoundException;

        PlayList createPlayList(String userId, String playListName, List<String> songIds)
                        throws UserNotFoundException, SongNotFoundException;

        PlayList addSongToPlayList(String userId, String playListName, String songId)
                        throws UserNotFoundException, SongNotFoundException;

        PlayList removeSongFromPlayList(String userId, String playListName, String songId)
                        throws UserNotFoundException, SongNotFoundException;

        void addPlaylistToUser(String userId, PlayList playlist) throws UserNotFoundException;

        void addSong(String playlistName, Song song, User user);

       void updatePlayList(String userId, PlayList existingPlayList,Song song);
}