package main.Commands.Player;

import main.Command;
import main.CommandVisitor;
import main.Commands.Types.Playlist;
import main.Commands.Types.Podcast;
import main.Commands.Types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetTop5Playlists implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<String> result;
    private static final int TOP_NR = 5;


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public GetTop5Playlists(final String command, final int timestamp) {
        this.command = command;
        this.timestamp = timestamp;
        result = new ArrayList<>();
    }

    /**
     * Search for the top 5 playlists
     *
     * @param everyPlaylists all the playlists
     */
    public void searchTop5Playlists(final ArrayList<Playlist> everyPlaylists) {

        ArrayList<Playlist> sortedPlaylists = new ArrayList<>(everyPlaylists);
        Collections.sort(sortedPlaylists, Comparator.
                comparingInt(Playlist::getFollowers).reversed());

        int i = 0;
        while (i < TOP_NR && i < sortedPlaylists.size()) {
            result.add(sortedPlaylists.get(i).getName());
            i++;
        }
    }

    /**
     * Getter for the command
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the timestamp
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for the result
     *
     * @return the result
     */
    public ArrayList<String> getResult() {
        return result;
    }

    /**
     * Execute the command
     */
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input,
                        final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {

        this.searchTop5Playlists(everyPlaylist);
    }
}
