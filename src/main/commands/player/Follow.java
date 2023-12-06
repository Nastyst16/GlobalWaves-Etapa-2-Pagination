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

public class Follow implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    public void execute(final User user, final ArrayList<Playlist> everyPlaylist) {
        this.setFollow(user, everyPlaylist);
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Follow(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Follows or unfollows a playlist
     * @param user the current user
     * @param everyPlaylist the list of all playlists
     */
    public void setFollow(final User user, final ArrayList<Playlist> everyPlaylist) {

//        if the user is offline
        if (!user.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

        if (user.getCurrentSelect() == null) {
            this.message = "Please select a source before following or unfollowing.";
            return;
        }
        if (user.getTypeSelected() != 2) {
            this.message = "The selected source is not a playlist.";
            return;
        }
        if (user.getUsername().equals(user.getSelectedPlaylist().getUser())) {
            this.message = "You cannot follow or unfollow your own playlist.";
            return;
        }

        int indexPlaylist = everyPlaylist.indexOf(user.getSelectedPlaylist());

        if (user.getFollowedPlaylists().contains(user.getSelectedPlaylist())) {

            everyPlaylist.get(indexPlaylist).decrementFollowers();
            user.getFollowedPlaylists().remove(user.getSelectedPlaylist());
            this.message = "Playlist unfollowed successfully.";

        } else {
            everyPlaylist.get(indexPlaylist).incrementFollowers();
            user.getFollowedPlaylists().add(user.getSelectedPlaylist());
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

}
