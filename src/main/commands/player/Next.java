package main.commands.player;

import main.Command;
import main.CommandVisitor;
import main.commands.types.*;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class Next implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    /**
     * execute the command
     */
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {

        user.setNext(true);
        this.setNext(user);
        user.setNext(false);
    }


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Next(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /** next command method-helper
     *
     * @param user the current user
     */
    public void setNext(final User user) {

//        if the user is offline
        if (user.getOnline() == false) {
            this.message = this.user + " is offline.";
            return;
        }

        if (user.getTypeLoaded() == 2 && user.getRepeatStatus() == 0) {
            int index = user.getCurrentPlaylist().
                    getSongList().indexOf(user.getCurrentType());
            if (user.getCurrentPlaylist().getSongList().size() == index + 1
                    && user.getCurrentPlaylist().getSongList().size() == 1) {

                user.setCurrentType(null);
            }
        }
        if (user.getCurrentType() == null || user.getTypeLoaded() == -1) {
            this.message = "Please load a source before skipping to the next track.";
            return;
        }
        user.setNext(true);

        Type currentType = user.getCurrentType();

        currentType.setSecondsGone(currentType.getDuration());

        user.treatingRepeatStatus(user);
        user.setPaused(false);

        user.setNext(false);

        if (user.getCurrentType() != null) {
            this.message = "Skipped to next track successfully. The current track is "
                    + user.getCurrentType().getName() + ".";
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

}
