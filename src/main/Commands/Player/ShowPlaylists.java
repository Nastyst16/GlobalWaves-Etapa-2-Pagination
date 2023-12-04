package main.Commands.Player;

import main.Command;
import main.CommandVisitor;
import main.Commands.Types.Playlist;
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
     *
     * @param command   the command
     * @param user      the user
     * @param timestamp the timestamp
     */
    public ShowPlaylists(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
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
    public void execute() {

    }
}
