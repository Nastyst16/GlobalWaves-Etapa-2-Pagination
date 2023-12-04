package main.Commands.Player;

import main.Command;
import main.CommandVisitor;
import main.Commands.Types.Type;
import main.User;

public class Prev implements Command {
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
    public Prev(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
    }

    /**
     * Returns to the previous track
     *
     * @param currentUser the user that called the command
     * @param currentType the current type
     */
    public void setPrev(final User currentUser) {
        Type currentType = currentUser.getCurrentType();

        if (currentUser.getCurrentType() == null) {
            this.message = "Please load a source before returning to the previous track.";
            return;
        }

//        if it's a song
        if (currentUser.getTypeLoaded() == 0) {
            currentType.setSecondsGone(0);
        } else if (currentUser.getTypeLoaded() == 2) {
//            prev for playlists

            if (currentType.getSecondsGone() > 0) {
                currentType.setSecondsGone(0);
            } else if (currentType.getSecondsGone() == 0) {
                if (currentUser.isShuffle()) {

                    int prevIndex = currentUser.getCurrentPlaylist().
                            getSongList().indexOf(currentType);
                    prevIndex = currentUser.getShuffledIndices().indexOf(prevIndex) - 1;

                    if (prevIndex == -1) {
                        currentType.setSecondsGone(0);
                    } else {
                        prevIndex = currentUser.getShuffledIndices().get(prevIndex);
                        currentType = currentUser.getCurrentPlaylist().getSongList().get(prevIndex);
                        currentType.setSecondsGone(0);
                    }

                } else {
//                    if it's the first song of the playlist
                    if (currentUser.getCurrentPlaylist().getSongList().indexOf(currentType) == 0) {
                        currentType.setSecondsGone(0);
                    } else {
                        int prevIndex = currentUser.getCurrentPlaylist().
                                getSongList().indexOf(currentType) - 1;
                        currentType = currentUser.getCurrentPlaylist().getSongList().get(prevIndex);
                        currentType.setSecondsGone(0);
                    }
                }
            }
        } else if (currentUser.getTypeLoaded() == 1) {
            if (currentUser.getCurrentPodcast().getLastRemainingEpisode() == 0) {
                currentType.setSecondsGone(0);
            } else {
                int prevIndex = currentUser.getCurrentPodcast().getLastRemainingEpisode() - 1;
                currentType = currentUser.getCurrentPodcast().getEpisodes().get(prevIndex);
            }
        }

        currentUser.setCurrentType(currentType);
        currentUser.setPaused(false);

        if (currentUser.getCurrentType() != null) {
            this.message = "Returned to previous track successfully. The current track is "
                    + currentType.getName() + ".";
        }

        currentUser.setCurrentType(currentType);
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
    public void execute() {

    }
}
