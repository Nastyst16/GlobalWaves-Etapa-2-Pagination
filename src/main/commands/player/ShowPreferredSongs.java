package main.commands.player;

import main.Command;
import main.CommandVisitor;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class ShowPreferredSongs implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final ArrayList<String> result;


    /**
     * Execute.
     */
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {

        this.setResult(user);
    }


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public ShowPreferredSongs(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
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
}
