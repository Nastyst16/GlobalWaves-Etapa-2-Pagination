package main.commands.player;

import main.Command;
import main.CommandVisitor;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class Repeat implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Repeat constructor
     * @param input the input
     */
    public Repeat(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Sets the repeat status of the current user
     * @param currentUser the current user
     * @param repeatStatus the current repeat status
     * @param typeLoaded the type of source loaded
     * @return the new repeat status
     */
    public int setRepeatMessage(final User currentUser, int repeatStatus, final int typeLoaded) {
        if (currentUser.getCurrentType() == null) {
            this.message = "Please load a source before setting the repeat status.";
            return -1;
        }

        if (typeLoaded == 0 || typeLoaded == 1) {
            if (repeatStatus == 0) {
                this.message = "Repeat mode changed to repeat once.";
                repeatStatus = 1;
                currentUser.setRepeatString("Repeat Once");
            } else if (repeatStatus == 1) {
                this.message = "Repeat mode changed to repeat infinite.";
                repeatStatus = 2;
                currentUser.setRepeatString("Repeat Infinite");
            } else if (repeatStatus == 2) {
                this.message = "Repeat mode changed to no repeat.";
                repeatStatus = 0;
                currentUser.setRepeatString("No Repeat");
            }
        } else {
            if (repeatStatus == 0) {
                this.message = "Repeat mode changed to repeat all.";
                repeatStatus = 1;
                currentUser.setRepeatString("Repeat All");
            } else if (repeatStatus == 1) {
                this.message = "Repeat mode changed to repeat current song.";
                repeatStatus = 2;
                currentUser.setRepeatString("Repeat Current Song");
            } else if (repeatStatus == 2) {
                this.message = "Repeat mode changed to no repeat.";
                repeatStatus = 0;
                currentUser.setRepeatString("No Repeat");
            }
        }

        return repeatStatus;
    }

    /**
     * Gets the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message
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

        user.setRepeatStatus(((Repeat) (commands.getLast())).setRepeatMessage(user,
                        user.getRepeatStatus(), user.getTypeLoaded()));
    }
}
