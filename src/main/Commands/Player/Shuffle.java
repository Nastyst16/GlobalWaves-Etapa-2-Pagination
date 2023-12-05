package main.Commands.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.Commands.Types.Playlist;
import main.Commands.Types.Podcast;
import main.Commands.Types.Song;
import main.Commands.Types.Type;
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


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     *
     * @param command   the command
     * @param user      the user
     * @param timestamp the timestamp
     * @param seed      the seed
     */
    public Shuffle(final String command, final String user, final int timestamp, final int seed) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        this.seed = seed;
    }

    /**
     * settingShuffle
     * @param currentUser the current user
     */
    public void settingShuffle(final User currentUser) {
        Type currentType = currentUser.getCurrentType();

        if (currentType != null && currentUser.getTypeLoaded() == 2) {

            if (!currentUser.isShuffle()) {
                this.message = "Shuffle function activated successfully.";
                currentUser.setShuffle(true);

                for (int i = 0; i < currentUser.getCurrentPlaylist().getSongList().size(); i++) {
                    currentUser.getOriginalIndices().add(i);
                }
                currentUser.getShuffledIndices().addAll(currentUser.getOriginalIndices());

                Random rand = new Random(this.seed);
                Collections.shuffle(currentUser.getShuffledIndices(), rand);

            } else {
                this.message = "Shuffle function deactivated successfully.";
                currentUser.setShuffle(false);

                currentUser.getOriginalIndices().clear();
                currentUser.getShuffledIndices().clear();

            }
            currentUser.setShuffleSeed(this.seed);

        } else if (currentType != null && currentUser.getTypeLoaded() != 2) {
            this.message = "The loaded source is not a playlist.";
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

    /**
     * Execute the command.
     */
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input,
                        final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {

        user.setShuffleSeed(input.getSeed());

        this.settingShuffle(user);
    }
}
