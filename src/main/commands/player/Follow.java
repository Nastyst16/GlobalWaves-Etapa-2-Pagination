package main.commands.player;

import main.Command;
import main.CommandVisitor;
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
    public void execute(final ArrayList<Command> commands, final SearchBar input,
                        final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {

        this.setFollow(user, everyPlaylist);
    }
}
