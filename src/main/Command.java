package main;

import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;

import java.util.ArrayList;

public interface Command {
    /**
     * Execute the command.
     */
    void execute(ArrayList<Command> commands, SearchBar input, User user, ArrayList<Song> songs,
                 ArrayList<Playlist> everyPlaylist, ArrayList<Podcast> podcasts);

    void accept(CommandVisitor visitor);
}
