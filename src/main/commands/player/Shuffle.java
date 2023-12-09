package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.commands.types.*;
import main.SearchBar;
import main.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Shuffle implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final int seed;
    private String message;


    /**
     * Execute the command.
     */
    public void execute(final SearchBar input, final User user) {

        user.setShuffleSeed(input.getSeed());

        this.settingShuffle(user);
    }


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Shuffle(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.seed = input.getSeed();
    }

    /**
     * settingShuffle
     * @param user the current user
     */
    public void settingShuffle(final User user) {

//        if the user is offline
        if (user.getOnline() == false) {
            this.message = this.user + " is offline.";
            return;
        }

        Type currentType = user.getCurrentType();

        if (currentType != null && user.getTypeLoaded() == 2) {

            if (!user.isShuffle()) {
                this.message = "Shuffle function activated successfully.";
                user.setShuffle(true);

                user.getOriginalIndices().clear();
                user.getShuffledIndices().clear();


                for (int i = 0; i < user.getCurrentPlaylist().getSongList().size(); i++) {
                    user.getOriginalIndices().add(i);
                }
                user.getShuffledIndices().addAll(user.getOriginalIndices());

                Random rand = new Random(this.seed);
                Collections.shuffle(user.getShuffledIndices(), rand);

            } else {
                this.message = "Shuffle function deactivated successfully.";
                user.setShuffle(false);

                user.getOriginalIndices().clear();
                user.getShuffledIndices().clear();

            }
            user.setShuffleSeed(this.seed);

        } else if (currentType != null && user.getTypeLoaded() != 2) {
            this.message = "The loaded source is not a playlist or an album.";
        } else if (currentType == null) {
            this.message = "Please load a source before using the shuffle function.";
        }
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }


    /**
     * Gets seed.
     *
     * @return the seed
     */
    @JsonIgnore
    public int getSeed() {
        return seed;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }


}
