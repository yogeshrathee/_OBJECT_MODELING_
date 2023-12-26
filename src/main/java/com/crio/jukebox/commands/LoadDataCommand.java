package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.Services.ISongService;
import com.crio.jukebox.Services.IUserPlayListService;
import com.crio.jukebox.exceptions.PlayListNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;


public class LoadDataCommand implements ICommand {

    private final ISongService songService;
    private final IUserPlayListService playListService;

    public LoadDataCommand(ISongService songService, IUserPlayListService playListService) {
        this.songService = songService;
        this.playListService = playListService;
    }

    public LoadDataCommand(ISongService songService2) {
        this.songService = songService2;
        this.playListService = null;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            playListService.addSongToPlayListByName(tokens.get(0), tokens.get(1), tokens.get(2));
            System.out.println("Song added to playlist successfully.");
        } catch (PlayListNotFoundException e) {
            System.out.println("Error: Playlist not found.");
            e.printStackTrace();
        } catch (SongNotFoundException e) {
            System.out.println("Error: Song not found.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred while adding the song to the playlist:");
            e.printStackTrace();
        } finally {
            // Close any resources used by the method here
        }
    }
}