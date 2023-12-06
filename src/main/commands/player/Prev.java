package main.commands.player;

import main.Command;
import main.CommandVisitor;
import main.commands.types.*;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class Prev implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * Executes the command
     */
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {

        this.setPrev(user);
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Prev(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Returns to the previous track
     *
     * @param user the user that called the command
     */
    public void setPrev(final User user) {

//        if the user is offline
        if (user.getOnline() == false) {
            this.message = this.user + " is offline.";
            return;
        }

        Type currentType = user.getCurrentType();

        if (user.getCurrentType() == null) {
            this.message = "Please load a source before returning to the previous track.";
            return;
        }

//        if it's a song
        if (user.getTypeLoaded() == 0) {
            currentType.setSecondsGone(0);
        } else if (user.getTypeLoaded() == 2) {
//            prev for playlists

            if (currentType.getSecondsGone() > 0) {
                currentType.setSecondsGone(0);
            } else if (currentType.getSecondsGone() == 0) {
                if (user.isShuffle()) {

                    int prevIndex = user.getCurrentPlaylist().
                            getSongList().indexOf(currentType);
                    prevIndex = user.getShuffledIndices().indexOf(prevIndex) - 1;

                    if (prevIndex == -1) {
                        currentType.setSecondsGone(0);
                    } else {
                        prevIndex = user.getShuffledIndices().get(prevIndex);
                        currentType = user.getCurrentPlaylist().getSongList().get(prevIndex);
                        currentType.setSecondsGone(0);
                    }

                } else {
//                    if it's the first song of the playlist
                    if (user.getCurrentPlaylist().getSongList().indexOf(currentType) == 0) {
                        currentType.setSecondsGone(0);
                    } else {
                        int prevIndex = user.getCurrentPlaylist().
                                getSongList().indexOf(currentType) - 1;
                        currentType = user.getCurrentPlaylist().getSongList().get(prevIndex);
                        currentType.setSecondsGone(0);
                    }
                }
            }
        } else if (user.getTypeLoaded() == 1) {
            if (user.getCurrentPodcast().getLastRemainingEpisode() == 0) {
                currentType.setSecondsGone(0);
            } else {
                int prevIndex = user.getCurrentPodcast().getLastRemainingEpisode() - 1;
                currentType = user.getCurrentPodcast().getEpisodes().get(prevIndex);
            }
        }

        user.setCurrentType(currentType);
        user.setPaused(false);

        if (user.getCurrentType() != null) {
            this.message = "Returned to previous track successfully. The current track is "
                    + currentType.getName() + ".";
        }

        user.setCurrentType(currentType);
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
