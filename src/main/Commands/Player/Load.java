package main.Commands.Player;

import main.*;
import main.Commands.Types.Playlist;
import main.Commands.Types.Podcast;
import main.Commands.Types.Song;

import java.util.ArrayList;

public class Load implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * Constructor
     *
     * @param command   the command to be executed
     * @param user      the user that called the command
     * @param timestamp the time when the command was called
     */
    public Load(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        this.message = "You can't load an empty audio collection!";
    }

    /** load command method
     *
     * @param currentUser current user
     * @param everyPlaylist every playlist
     * @param podcasts every podcast
     */
    public void setLoad(final User currentUser, final ArrayList<Playlist> everyPlaylist,
                    final ArrayList<Podcast> podcasts) {

//          if the last command was select
        if (currentUser.getCurrentSelect() != null) {
//          if the last selection was succesfully we can do the load
//          boolean selectSuccessful = currentSelect.getMessage().contains("Successfully");
        boolean selectSuccessful = currentUser.getCurrentSelect().
                getMessage().contains("Successfully");

        if (selectSuccessful) {
            this.message = "Playback loaded successfully.";

            currentUser.setLoadMade(1);
            currentUser.setPaused(false);
            currentUser.setRepeatStatus(0);
            currentUser.setRepeatString("No Repeat");
            currentUser.setSecondsGone(0);

            if (currentUser.getTypeSelected() == 0) {
                for (Song song : currentUser.getEverySong()) {
                    if (song.getName().equals(currentUser.
                            getCurrentSelect().getSelectedName())) {
                        currentUser.setCurrentType(song);

                        currentUser.getCurrentType().setSecondsGone(0);

                        currentUser.setTypeLoaded(0);
                        break;
                    }
                }
            } else if (currentUser.getTypeSelected() == 1) {
                for (Podcast podcast : podcasts) {
                    if (podcast.getName().equals(currentUser.
                            getCurrentSelect().getSelectedName())) {

                        if (currentUser.getPodcastsPlayed().contains(podcast)) {
                            int indexPodcast = currentUser.getPodcastsPlayed().indexOf(podcast);

                            currentUser.setCurrentType(currentUser.getPodcastsPlayed().
                                    get(indexPodcast));
                            int indexEpisode = ((Podcast) (currentUser.getCurrentType())).
                                    getLastRemainingEpisode();
                            currentUser.setCurrentType(((Podcast) (currentUser.
                                    getCurrentType())).getEpisodes().get(indexEpisode));

                            currentUser.setCurrentPodcast(podcast);

                        } else {
//                              adding to the currentUser the loaded podcast
                            currentUser.addPodcastPlayed(podcast);

                            int lastPodcast = currentUser.getPodcastsPlayed().size() - 1;
                            int lastEpisode = currentUser.getPodcastsPlayed().
                                    get(lastPodcast).getEpisodes().size() - 1;

//                              setting last episode watched to 0
                            currentUser.getPodcastsPlayed().get(lastPodcast).setLastRemainingEpisode(0);
//                              setting the remaining second;
                            currentUser.getPodcastsPlayed().get(lastPodcast).getEpisodes().
                                    get(lastEpisode).setSecondsGone(0);

                            currentUser.setRemainingTime(podcast.getEpisodes().
                                    get(lastEpisode).getDuration());

//                               current type is Podcast
                            currentUser.setCurrentType(currentUser.getPodcastsPlayed().get(lastPodcast));

//                               current type is Episode
                            currentUser.setCurrentType(((Podcast) (currentUser.getCurrentType())).
                                    getEpisodes().get(0));

                            currentUser.setCurrentPodcast(podcast);
                        }
                        currentUser.setTypeLoaded(1);
                        break;
                    }
                }
            } else if (currentUser.getTypeSelected() == 2) {
                for (Playlist playlist : everyPlaylist) {
                    if (playlist.getName().equals(currentUser.
                            getCurrentSelect().getSelectedName())) {
                        currentUser.setTypeLoaded(2);

                        currentUser.setCurrentPlaylist(playlist);
                        currentUser.setCurrentType(playlist.getSongList().get(0));
                        currentUser.setRemainingTime(currentUser.getCurrentType().getDuration());
                        currentUser.getCurrentType().setSecondsGone(0);

                        break;
                    }
                }
            }
        } else {
            this.message = "Please select the song first.";
        }
    } else {
        this.message = "Please select a source before attempting to load.";
    }
    if (currentUser.getCurrentType() != null) {
        currentUser.setSelectedName((String) (currentUser.getCurrentType().getName()));
    }

    currentUser.setCurrentSelect(null);
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

    @Override
    public void execute() {

    }
}
