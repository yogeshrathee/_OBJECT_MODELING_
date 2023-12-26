package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.Services.IUserPlayListService;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.PlayListNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.IPlayListRepository;
import com.crio.jukebox.repositories.IUserRepository;

public class AddSongToPlayListCommand implements ICommand {
    private final IPlayListRepository playListService;
    private User user;
    private IUserRepository userRepository;
    private IUserPlayListService userPlayListService;

    public AddSongToPlayListCommand(IPlayListRepository userPlayListService) {
        this.playListService = userPlayListService;
        this.userRepository = null;
        this.userPlayListService = null;
    }

    // private final IUserPlayListService userPlayListService;

    public AddSongToPlayListCommand(IUserPlayListService userPlayListService) {
        this.userPlayListService = userPlayListService;
        this.userRepository = null;
        this.playListService = null;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            userPlayListService.addSongToPlayListByName(tokens.get(0), "My Playlist",
                    tokens.get(2));
            System.out.println("Added Succefully\n");
        } catch (UserNotFoundException e) {
            System.out.println("User Not Found!!! Try Again Later\n");
        } catch (PlayListNotFoundException e) {
            System.out.println("Playlist Not Found\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}