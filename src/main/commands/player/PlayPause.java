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

public class PlayPause implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    /**
     * Executes the command
     */
    public void execute(final User user) {
        this.setPlayPause(user);
    }


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public PlayPause(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.message = "Please load a source before attempting to pause or resume playback.";
    }

    /**
     * Pauses or resumes playback
     *
     * @param user the user that called the command
     */
    public void setPlayPause(final User user) {

//        if the user is offline
        if (user.getOnline() == false) {
            this.message = this.user + " is offline.";
            return;
        }

        if (!user.isPaused() && user.getTypeLoaded() != -1) {
            this.message = "Playback paused successfully.";

            user.setPaused(true);

        } else if (user.isPaused() && user.getTypeLoaded() != -1) {
            this.message = "Playback resumed successfully.";

            user.setPaused(false);
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


}
