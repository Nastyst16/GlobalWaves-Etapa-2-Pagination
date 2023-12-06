package main.commands;

import main.Command;
import main.CommandVisitor;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class Default implements Command {


    public Default() {

    }

    public void execute() {
        System.out.println("Invalid command");
    }

    @Override
    public void accept(CommandVisitor visitor) {
    }


}
