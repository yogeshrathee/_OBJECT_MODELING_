package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.codingame.commands.ICommand;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.Services.IUserPlayListService;
import com.crio.jukebox.dto.CustomPlayListCreated;
import com.crio.jukebox.exceptions.SongNotFoundException;

public class CreateNewPlayListCommand implements ICommand {
    public CreateNewPlayListCommand(IUserPlayListService userPlayListService2) {}


    public void CreatePlayListCommand(IUserPlayListService userPlayListService) {
        this.userPlayListService = userPlayListService;
    }

    private IUserPlayListService userPlayListService;


    @Override
    public void execute(List<String> tokens) {
        try {
            List<String> csong = new ArrayList<String>();
            for (int i = 3; i < tokens.size(); i++)
                csong.add(tokens.get(i));
            CustomPlayListCreated playList =
                    userPlayListService.createPlayList("1", "Custom Playlist", new ArrayList<>());
            System.out.println(playList);
        } catch (UserNotFoundException e) {
            System.out.println("User is Not Present. Please try again");
        } catch (SongNotFoundException e) {
            System.out.println("Some Requested Songs Not Available. Please try again.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}