package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

/**
 * Represents a command to add or remove a song in a playlist.
 * Implements the {@link Command} interface for execution.
 */
public class AddRemoveInPlaylist implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final int playlistId;
    private String message;


    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {
//        setting message
        this.setMessage(user, input.getPlaylistId());
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructs an {@code AddRemoveInPlaylist} object with the specified parameters.
     * @param input The input {@code SearchBar} object.
     */
    public AddRemoveInPlaylist(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.playlistId = input.getPlaylistId();
    }


    /**
     * Sets the message based on the execution of the command.
     *
     * @param user The current user issuing the command.
     * @param index       The index of the playlist in the user's playlist list.
     */
    public void setMessage(final User user, final int index) {

//        if the user is offline
        if (!user.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

        if (user.getTypeLoaded() == 2
                || user.getTypeLoaded() == 1) {
            this.message = "The loaded source is not a song.";
            return;
        }

        if (user.getCurrentType() == null) {
            this.message = "Please load a source before adding to or removing from the playlist.";
        } else {
            if (index > user.getPlayListList().size()) {
                this.message = "The specified playlist does not exist.";
            } else {
                if (user.getTypeSelected() == 2
                        || user.getTypeSelected() == 1) {
                    this.message = "The loaded source is not a song.";
                } else {
                    this.message = user.getPlayListList().get(index - 1).
                            addRemoveSong((Song) user.getCurrentType());
                }
            }
        }
    }



    /**
     * Gets the command string associated with this {@code AddRemoveInPlaylist} instance.
     *
     * @return The command string.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the user associated with this {@code AddRemoveInPlaylist} instance.
     *
     * @return The user string.
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the timestamp associated with this {@code AddRemoveInPlaylist} instance.
     *
     * @return The timestamp.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the playlist ID associated with this {@code AddRemoveInPlaylist} instance.
     *
     * @return The playlist ID.
     */
    @JsonIgnore
    public int getPlaylistId() {
        return playlistId;
    }

    /**
     * Gets the message associated with the execution of the command.
     *
     * @return The message string.
     */
    public String getMessage() {
        return message;
    }

}