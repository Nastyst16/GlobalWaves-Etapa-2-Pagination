package main.commands.player;

import main.Command;
import main.CommandVisitor;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class Backward implements Command {
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
    public void execute(final ArrayList<Command> commands, final SearchBar input,
                        final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {

        this.setBackward(user);
    }
}
