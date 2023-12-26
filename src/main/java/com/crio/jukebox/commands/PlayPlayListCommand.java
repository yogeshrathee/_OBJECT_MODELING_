package com.crio.jukebox.commands;

import java.util.List;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.Services.IUserPlayListService;
import com.crio.jukebox.dto.UserPlayedSongDto;
import com.crio.jukebox.exceptions.PlayListNotFoundException;

public class PlayPlayListCommand implements ICommand {
    private final IUserPlayListService userPlayListService;

    public  PlayPlayListCommand(IUserPlayListService userPlayListService) {
        this.userPlayListService = userPlayListService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            UserPlayedSongDto playedSongDto =
                    userPlayListService.setCurrentPlayList(tokens.get(1), tokens.get(2));
            System.out.println(playedSongDto);
        } catch (UserNotFoundException e) {
            System.out.println("User Not Found");
        } catch (PlayListNotFoundException e) {
            System.out.println("Playlist is empty");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
}