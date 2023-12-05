package main.commands.player;

import main.Command;
import main.CommandVisitor;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class Forward implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;
    private static final int SECONDS_TO_FORWARD = 90;


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Forward(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Skips forward 90 seconds
     * @param currentUser the current user
     */
    public void setForward(final User currentUser) {
        if (currentUser.getCurrentType() == null) {
            this.message = "Please load a source before attempting to forward.";
            return;
        }
        if (currentUser.getTypeLoaded() != 1) {
            this.message = "The loaded source is not a podcast.";
            return;
        }

        currentUser.getCurrentType().setSecondsGone(currentUser.
                getCurrentType().getSecondsGone() + SECONDS_TO_FORWARD);
        this.message = "Skipped forward successfully.";
    }

    /**
     * Getter for the message
     * @return the message
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Executes the command
     */
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input,
                        final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {

        this.setForward(user);
    }
}
