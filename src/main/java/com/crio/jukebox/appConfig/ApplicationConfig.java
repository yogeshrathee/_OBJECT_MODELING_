package com.crio.jukebox.appConfig;

import com.crio.jukebox.Services.ISongService;
import com.crio.jukebox.Services.IUserPlayListService;
import com.crio.jukebox.Services.IUserService;
import com.crio.jukebox.Services.SongService;
import com.crio.jukebox.Services.UserPlayListService;
import com.crio.jukebox.Services.UserService;
import com.crio.jukebox.commands.CommandInvoker;
import com.crio.jukebox.commands.CreateNewPlayListCommand;
import com.crio.jukebox.commands.CreateUserCommand;
import com.crio.jukebox.commands.DeletePlayListCommand;
import com.crio.jukebox.commands.LoadDataCommand;
import com.crio.jukebox.commands.ModifyPlayListCommand;
import com.crio.jukebox.commands.PlayPlayListCommand;
import com.crio.jukebox.commands.PlaySongFromPlayListCommand;
import com.crio.jukebox.repositories.AlbumRepository;
import com.crio.jukebox.repositories.IAlbumRepository;
import com.crio.jukebox.repositories.IPlayListRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserPlayListRepository;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.repositories.PlayListRepository;
import com.crio.jukebox.repositories.SongRepository;
import com.crio.jukebox.repositories.UserPlayListRepository;
import com.crio.jukebox.repositories.UserRepository;

public class ApplicationConfig {
    private final IAlbumRepository albumRepository = new AlbumRepository();
    private final ISongRepository songRepository = new SongRepository();
    private final IUserPlayListRepository userPlayListRepository = new UserPlayListRepository();
    private final IUserRepository userRepository =
            new UserRepository(userPlayListRepository, songRepository);
    private final IPlayListRepository playListRepository = new PlayListRepository(songRepository);

    private final IUserService userService =
            new UserService(userRepository, albumRepository, songRepository);
    private final ISongService songService = new SongService(songRepository, albumRepository);
    private final IUserPlayListService userPlayListService =
            new UserPlayListService(userRepository, playListRepository, songRepository);


    private final CreateUserCommand createUserCommand = new CreateUserCommand(userService);
    private final LoadDataCommand loadDataCommand = new LoadDataCommand(songService);
    private final CreateNewPlayListCommand createPlayListCommand =
            new CreateNewPlayListCommand(userPlayListService);
    private final DeletePlayListCommand deletePlayListCommand =
            new DeletePlayListCommand(userPlayListService);
    private final ModifyPlayListCommand modifyPlayListCommand =
            new ModifyPlayListCommand(userPlayListService);
    private final PlayPlayListCommand playPlayListCommand =
            new PlayPlayListCommand(userPlayListService);
    private final PlaySongFromPlayListCommand playSongOnPlayListCommand =
            new PlaySongFromPlayListCommand(userPlayListService);

    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker() {
        commandInvoker.register("CREATE-USER", createUserCommand);
        commandInvoker.register("LOAD-DATA", loadDataCommand);
        commandInvoker.register("CREATE-PLAYLIST", createPlayListCommand);
        commandInvoker.register("DELETE-PLAYLIST", deletePlayListCommand);
        commandInvoker.register("MODIFY-PLAYLIST", modifyPlayListCommand);
        commandInvoker.register("PLAY-PLAYLIST", playPlayListCommand);
        commandInvoker.register("PLAY-SONG", playSongOnPlayListCommand);
        return commandInvoker;
    }

}