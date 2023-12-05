package main.commands.player;

import main.Command;
import main.CommandVisitor;
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
    public void execute(final ArrayList<Command> commands, final SearchBar input,
                        final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {
        this.setPlayPause(user);
    }
}
