package main.Commands.Player;

import main.Command;
import main.User;

public class PlayPause implements Command {
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
    public PlayPause(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        this.message = "Please load a source before attempting to pause or resume playback.";
    }

    /**
     * Pauses or resumes playback
     *
     * @param currentUser the user that called the command
     */
    public void setPlayPause(final User currentUser) {

        if (!currentUser.isPaused() && currentUser.getTypeLoaded() != -1) {
            this.message = "Playback paused successfully.";

            currentUser.setPaused(true);

        } else if (currentUser.isPaused() && currentUser.getTypeLoaded() != -1) {
            this.message = "Playback resumed successfully.";

            currentUser.setPaused(false);
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
     * Executes the command
     */
    @Override
    public void execute() {

    }
}
