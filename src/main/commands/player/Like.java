package main.commands.player;

import main.Command;
import main.CommandVisitor;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class Like implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    /**
     * executes the command
     */
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {

        this.likeHelper(user, songs);
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Like(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Sets the message to be displayed if the like was successful or not
     *
     * @param like true if the like was successful, false otherwise
     */
    public void setMessageIfLiked(final boolean like) {
        if (like) {
            setMessage("Like registered successfully.");
        } else {
            setMessage("Unlike registered successfully.");
        }
    }

    /**
     * evaluates the command
     *
     * @param user the user that called the command
     * @param songs       the list of songs
     */
    public void likeHelper(final User user, final ArrayList<Song> songs) {

        if (user.getOnline() == false) {
            this.message = this.user + " is offline.";
            return;
        }

        if (user.getCurrentType() != null) {
//                    if we have loaded a song
            boolean like = user.setLikedSongs((Song) user.getCurrentType(), songs);
            this.setMessageIfLiked(like);
        } else if (user.getTypeLoaded() == 2) {
//                    if we have loaded a playlist
            boolean like = user.setLikedPlaylist();
            this.setMessageIfLiked(like);
        } else {
            this.message = "Please load a source before liking or unliking.";
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

}
