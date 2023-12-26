package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.*;
import com.crio.jukebox.Services.IUserService;
import com.crio.jukebox.entities.User;

public class CreateUserCommand implements ICommand {
    private final IUserService userService;

    public CreateUserCommand(com.crio.jukebox.Services.IUserService userService2) {
        this.userService = userService2;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            User user = userService.create(tokens.get(1));
            System.out.println(user);
        } catch (Exception e) {
            System.out.println("Something Go Wrong While Creating User");
        }
    }
}