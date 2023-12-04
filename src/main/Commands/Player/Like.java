package main.Commands.Player;

import main.Command;
import main.Commands.Types.Song;
import main.User;

import java.util.ArrayList;

public class Like implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * Constructor
     *
     * @param command   the command to be executed
     * @param user      the user that called the command
     * @param timestamp the time when the command was called
     */
    public Like(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
    }

    /**
     * Sets the message to be displayed if the like was successful or not
     *
     * @param like true if the like was successful, false otherwise
     */
    public void setMessageIfLiked(final boolean like) {
        if (like) {
            setMessage("Like registered successfully.");
        } else {
            setMessage("Unlike registered successfully.");
        }
    }

    /**
     * evaluates the command
     *
     * @param currentUser the user that called the command
     * @param songs       the list of songs
     */
    public void likeHelper(final User currentUser, final ArrayList<Song> songs) {

        if (currentUser.getCurrentType() != null) {
//                    if we have loaded a song
            boolean like = currentUser.setLikedSongs((Song) currentUser.getCurrentType(), songs);
            this.setMessageIfLiked(like);
        } else if (currentUser.getTypeLoaded() == 2) {
//                    if we have loaded a playlist
            boolean like = currentUser.setLikedPlaylist();
            this.setMessageIfLiked(like);
        } else {
            this.message = "Please load a source before liking or unliking.";
        }
    }

    /**
     * gets the command
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the timestamp
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message
     *
     * @param message the message to be set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * executes the command
     */
    @Override
    public void execute() {

    }
}
