package main.Commands.Player;

import main.Command;
import main.User;

public class Backward implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;
    private static final int SECONDS_TO_FORWARD = 90;

    /**
     * Constructor.
     *
     * @param command   The command string.
     * @param user      The user string.
     * @param timestamp The timestamp.
     */
    public Backward(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
    }

    /**
     * goes back 90 seconds in the current track
     * @param currentUser the current user
     */
    public void setBackward(final User currentUser) {
        if (currentUser.getTypeLoaded() != 1) {
            this.message = "The loaded source is not a podcast.";
            return;
        }
        if (currentUser.getCurrentType() == null) {
            this.message = "Please load a source before returning to the previous track.";
            return;
        }
        currentUser.getCurrentType().setSecondsGone(currentUser.
                getCurrentType().getSecondsGone() - SECONDS_TO_FORWARD);
        this.message = "Rewound successfully.";
    }



    /**
     * Gets the command string.
     *
     * @return The command string.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the user string.
     *
     * @return The user string.
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the timestamp.
     *
     * @return The timestamp.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the message.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message The message.
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Executes the command.
     */
    @Override
    public void execute() {

    }
}
