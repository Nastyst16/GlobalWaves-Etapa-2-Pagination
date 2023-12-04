package main.Commands.Player;

import main.Command;
import main.Commands.Types.Playlist;
import main.User;
import java.util.ArrayList;

public class Follow implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    /**
     * Constructor
     * @param command the command
     * @param user the user
     * @param timestamp the timestamp
     */
    public Follow(final String command, final  String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
    }

    /**
     * Follows or unfollows a playlist
     * @param currentUser the current user
     * @param everyPlaylist the list of all playlists
     */
    public void setFollow(final User currentUser, final ArrayList<Playlist> everyPlaylist) {

        if (currentUser.getCurrentSelect() == null) {
            this.message = "Please select a source before following or unfollowing.";
            return;
        }
        if (currentUser.getTypeSelected() != 2) {
            this.message = "The selected source is not a playlist.";
            return;
        }
        if (currentUser.getUsername().equals(currentUser.getSelectedPlaylist().getUser())) {
            this.message = "You cannot follow or unfollow your own playlist.";
            return;
        }

        int indexPlaylist = everyPlaylist.indexOf(currentUser.getSelectedPlaylist());

        if (currentUser.getFollowedPlaylists().contains(currentUser.getSelectedPlaylist())) {

            everyPlaylist.get(indexPlaylist).decrementFollowers();
            currentUser.getFollowedPlaylists().remove(currentUser.getSelectedPlaylist());
            this.message = "Playlist unfollowed successfully.";

        } else {
            everyPlaylist.get(indexPlaylist).incrementFollowers();
            currentUser.getFollowedPlaylists().add(currentUser.getSelectedPlaylist());
            this.message = "Playlist followed successfully.";
        }
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message
     * @param message the message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public void execute() {

    }
}
