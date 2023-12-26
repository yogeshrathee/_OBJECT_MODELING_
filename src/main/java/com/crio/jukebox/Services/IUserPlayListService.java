package com.crio.jukebox.Services;

import java.util.List;
import com.crio.jukebox.dto.CustomPlayListCreated;
import com.crio.jukebox.dto.UserPlayedSongDto;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.SongPlayingOrder;

public interface IUserPlayListService {

        PlayList deleteSongFromPlayList(String userId, String playListName, String songName);
        PlayList deleteSongListFromPlayList(String userId, String playListName,  List<String> songsNameList);
        PlayList addSongToPlayList(String userId, String string, List<String> csong);

        UserPlayedSongDto setCurrentPlayList(String string, String string2);
        PlayList addSongToPlayListByName(String userId, String playlistName, String song);


        UserPlayedSongDto playSongByName(String string, String Id);

        UserPlayedSongDto playSongByOrder(String string, SongPlayingOrder back);

        CustomPlayListCreated createPlayList(String id, String name, List<Song> playListSongs);

        PlayList addSongToPlayListByList(String userId, PlayList playList, List<Song> songsToAdd);

        PlayList addSongToPlayListByNameString(String userId, PlayList playlist, String song);

}