package main;

import main.Commands.SearchBar.Search;
import main.Commands.Types.Playlist;
import main.Commands.Types.Podcast;
import main.Commands.Types.Song;

import java.util.ArrayList;

public interface Command {
    /**
     * Execute the command.
     */
    void execute(ArrayList<Command> commands, SearchBar input, User user, ArrayList<Song> songs,
                 ArrayList<Playlist> everyPlaylist, ArrayList<Podcast> podcasts);

    void accept(CommandVisitor visitor);
}
