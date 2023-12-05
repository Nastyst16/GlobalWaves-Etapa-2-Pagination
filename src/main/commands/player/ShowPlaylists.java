package main.commands.player;

import main.Command;
import main.CommandVisitor;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class ShowPlaylists implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final ArrayList<Playlist> result;

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public ShowPlaylists(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.result = new ArrayList<>();
    }

    /**
     * Copy playlists.
     *
     * @param currentUser the current user
     * @param copyList    the copy list
     */
    public void copyPlaylists(final User currentUser, final ArrayList<Playlist> copyList) {

        for (int i = 0; i < currentUser.getPlayListList().size(); i++) {
            copyList.add(new Playlist(currentUser.getPlayListList().get(i)));
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
    public ArrayList<Playlist> getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     */
    public void setResult(final ArrayList<Playlist> result) {
        this.result.clear();
        this.result.addAll(result);
    }

    /**
     * Execute the command.
     */
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input,
                        final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {


//                copying the playlists
        ArrayList<Playlist> copyList = new ArrayList<>();
        this.copyPlaylists(user, copyList);

        this.setResult(copyList);
    }
}
