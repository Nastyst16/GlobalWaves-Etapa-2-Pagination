package main.Commands.Player;

import main.Command;
import main.CommandVisitor;
import main.Commands.Types.Type;
import main.User;

public class Next implements Command {
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
     *
     * @param command   the command to be executed
     * @param user      the user that called the command
     * @param timestamp the time when the command was called
     */
    public Next(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
    }

    /** next command method-helper
     *
     * @param currentUser the current user
     */
    public void setNext(final User currentUser) {

        if (currentUser.getTypeLoaded() == 2 && currentUser.getRepeatStatus() == 0) {
            int index = currentUser.getCurrentPlaylist().
                    getSongList().indexOf(currentUser.getCurrentType());
            if (currentUser.getCurrentPlaylist().getSongList().size() == index + 1
                    && currentUser.getCurrentPlaylist().getSongList().size() == 1) {

                currentUser.setCurrentType(null);
            }
        }
        if (currentUser.getCurrentType() == null || currentUser.getTypeLoaded() == -1) {
            this.message = "Please load a source before skipping to the next track.";
            return;
        }
        currentUser.setNext(true);

        Type currentType = currentUser.getCurrentType();

        currentType.setSecondsGone(currentType.getDuration());

        currentUser.treatingRepeatStatus(currentUser);
        currentUser.setPaused(false);

        currentUser.setNext(false);

        if (currentUser.getCurrentType() != null) {
            this.message = "Skipped to next track successfully. The current track is "
                    + currentUser.getCurrentType().getName() + ".";
        } else {
            this.message = "Please load a source before skipping to the next track.";
        }
    }

    /**
     * getter for the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * getter for the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message
     * @param message the message to be set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * execute the command
     */
    @Override
    public void execute() {

    }
}
