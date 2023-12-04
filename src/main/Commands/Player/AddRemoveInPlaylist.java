package main.Commands.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.Commands.Types.Song;
import main.User;

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
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructs an {@code AddRemoveInPlaylist} object with the specified parameters.
     *
     * @param command    The command string indicating the operation (add/remove).
     * @param user       The user associated with the command.
     * @param timestamp  The timestamp of when the command was created.
     * @param playlistId The ID of the playlist affected by the command.
     */
    public AddRemoveInPlaylist(final String command, final String user,
                               final int timestamp, final int playlistId) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        this.playlistId = playlistId;
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


    /**
     * Sets the message based on the execution of the command.
     *
     * @param currentUser The current user issuing the command.
     * @param index       The index of the playlist in the user's playlist list.
     */
    public void setMessage(final User currentUser, final int index) {
        if (currentUser.getTypeLoaded() == 2
                || currentUser.getTypeLoaded() == 1) {
            this.message = "The loaded source is not a song.";
            return;
        }

        if (currentUser.getCurrentType() == null) {
            this.message = "Please load a source before adding to or removing from the playlist.";
        } else {
            if (index > currentUser.getPlayListList().size()) {
                this.message = "The specified playlist does not exist.";
            } else {
                if (currentUser.getTypeSelected() == 2
                        || currentUser.getTypeSelected() == 1) {
                    this.message = "The loaded source is not a song.";
                } else {
                    this.message = currentUser.getPlayListList().get(index - 1).
                            addRemoveSong((Song) currentUser.getCurrentType());
                }
            }
        }
    }

    @Override
    public void execute() {

    }
}
