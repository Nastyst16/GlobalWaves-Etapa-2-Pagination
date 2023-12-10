package main.commands.player.statistics;

import main.Command;
import main.CommandVisitor;
import main.commands.types.Playlist;
import main.SearchBar;
import main.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class GetTop5Playlists implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<String> result;
    private static final int TOP_NR = 5;


    /**
     * Execute the command
     */
    public void execute(final ArrayList<Playlist> everyPlaylist, final ArrayList<User> users) {
        this.searchTop5Playlists(everyPlaylist);
    }

    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public GetTop5Playlists(final SearchBar input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
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

}
