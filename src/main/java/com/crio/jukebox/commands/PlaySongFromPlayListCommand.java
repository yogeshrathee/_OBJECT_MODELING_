package com.crio.jukebox.commands;

import java.util.List;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.Services.IUserPlayListService;
import com.crio.jukebox.dto.UserPlayedSongDto;
import com.crio.jukebox.entities.SongPlayingOrder;
import com.crio.jukebox.exceptions.SongNotFoundException;

public class PlaySongFromPlayListCommand implements ICommand {
    private final IUserPlayListService userPlayListService;

    public PlaySongFromPlayListCommand(IUserPlayListService userPlayListService) {
        this.userPlayListService = userPlayListService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            UserPlayedSongDto playedSongDto;
            if (!tokens.get(2).equals("BACK") && !tokens.get(2).equals("NEXT")) {
                playedSongDto = userPlayListService.playSongByName(tokens.get(1), tokens.get(2));
            } else if (tokens.get(2).equals("BACK")) {
                playedSongDto =
                        userPlayListService.playSongByOrder(tokens.get(1), SongPlayingOrder.BACK);
            } else {
                playedSongDto =
                        userPlayListService.playSongByOrder(tokens.get(1), SongPlayingOrder.NEXT);
            }
            System.out.println(playedSongDto);
        } catch (UserNotFoundException e) {
            System.out.println("User not found");
        } catch (SongNotFoundException e) {
            System.out.println("Song Not Found in the current active playlist");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}