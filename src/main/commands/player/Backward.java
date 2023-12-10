package main.commands.player;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.users.User;

public class Backward implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;
    private static final int SECONDS_TO_FORWARD = 90;


    /**
     * Executes the command.
     */
    public void execute(final User user) {

        this.setBackward(user);
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor.
     * @param input the input
     */
    public Backward(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * goes back 90 seconds in the current track
     * @param user the current user
     */
    public void setBackward(final User user) {

//        if the user is offline
        if (user.getOnline() == false) {
            this.message = this.user + " is offline.";
            return;
        }

        if (user.getTypeLoaded() != 1) {
            this.message = "The loaded source is not a podcast.";
            return;
        }
        if (user.getCurrentType() == null) {
            this.message = "Please load a source before returning to the previous track.";
            return;
        }
        user.getCurrentType().setSecondsGone(user.
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


}
