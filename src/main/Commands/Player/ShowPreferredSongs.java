package main.Commands.Player;

import main.Command;
import main.CommandVisitor;
import main.Commands.Types.Playlist;
import main.Commands.Types.Podcast;
import main.Commands.Types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class ShowPreferredSongs implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final ArrayList<String> result;


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     *
     * @param command   the command
     * @param user      the user
     * @param timestamp the timestamp
     */
    public ShowPreferredSongs(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        result = new ArrayList<>();
    }

    /**
     * Sets result.
     *
     * @param user the user
     */
    public void setResult(final User user) {
        if (!user.getLikedSongs().isEmpty()) {
            for (Song song : user.getLikedSongs()) {
                this.result.add(song.getName());
            }
        }
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public ArrayList<String> getResult() {
        return result;
    }

    /**
     * Execute.
     */
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input,
                        final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {

        this.setResult(user);
    }
}
