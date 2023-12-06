package main.commands.player;

import main.*;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;

import java.util.ArrayList;

public class Load implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {

        this.setLoad(user, everyPlaylist, podcasts);
    }



    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Load(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.message = "You can't load an empty audio collection!";
    }

    /** load command method
     *
     * @param user current user
     * @param everyPlaylist every playlist
     * @param podcasts every podcast
     */
    public void setLoad(final User user, final ArrayList<Playlist> everyPlaylist,
                    final ArrayList<Podcast> podcasts) {

//        if the user is offline
        if (user.getOnline() == false) {
            this.message = this.user + " is offline.";
            return;
        }

//          if the last command was select
        if (user.getCurrentSelect() != null) {
//          if the last selection was succesfully we can do the load
//          boolean selectSuccessful = currentSelect.getMessage().contains("Successfully");
        boolean selectSuccessful = user.getCurrentSelect().
                getMessage().contains("Successfully");

        if (selectSuccessful) {
            this.message = "Playback loaded successfully.";

            user.setLoadMade(1);
            user.setPaused(false);
            user.setRepeatStatus(0);
            user.setRepeatString("No Repeat");
            user.setSecondsGone(0);

            if (user.getTypeSelected() == 0) {
                for (Song song : user.getEverySong()) {
                    if (song.getName().equals(user.
                            getCurrentSelect().getSelectedName())) {
                        user.setCurrentType(song);

                        user.getCurrentType().setSecondsGone(0);

                        user.setTypeLoaded(0);
                        break;
                    }
                }
            } else if (user.getTypeSelected() == 1) {
                for (Podcast podcast : podcasts) {
                    if (podcast.getName().equals(user.
                            getCurrentSelect().getSelectedName())) {

                        if (user.getPodcastsPlayed().contains(podcast)) {
                            int indexPodcast = user.getPodcastsPlayed().indexOf(podcast);

                            user.setCurrentType(user.getPodcastsPlayed().
                                    get(indexPodcast));
                            int indexEpisode = ((Podcast) (user.getCurrentType())).
                                    getLastRemainingEpisode();
                            user.setCurrentType(((Podcast) (user.
                                    getCurrentType())).getEpisodes().get(indexEpisode));

                            user.setCurrentPodcast(podcast);

                        } else {
//                              adding to the user the loaded podcast
                            user.addPodcastPlayed(podcast);

                            int lastPodcast = user.getPodcastsPlayed().size() - 1;
                            int lastEpisode = user.getPodcastsPlayed().
                                    get(lastPodcast).getEpisodes().size() - 1;

//                              setting last episode watched to 0
                            user.getPodcastsPlayed().get(lastPodcast).setLastRemainingEpisode(0);
//                              setting the remaining second;
                            user.getPodcastsPlayed().get(lastPodcast).getEpisodes().
                                    get(lastEpisode).setSecondsGone(0);

                            user.setRemainingTime(podcast.getEpisodes().
                                    get(lastEpisode).getDuration());

//                               current type is Podcast
                            user.setCurrentType(user.getPodcastsPlayed().get(lastPodcast));

//                               current type is Episode
                            user.setCurrentType(((Podcast) (user.getCurrentType())).
                                    getEpisodes().get(0));

                            user.setCurrentPodcast(podcast);
                        }
                        user.setTypeLoaded(1);
                        break;
                    }
                }
            } else if (user.getTypeSelected() == 2) {
                for (Playlist playlist : everyPlaylist) {
                    if (playlist.getName().equals(user.
                            getCurrentSelect().getSelectedName())) {
                        user.setTypeLoaded(2);

                        user.setCurrentPlaylist(playlist);
                        user.setCurrentType(playlist.getSongList().get(0));
                        user.setRemainingTime(user.getCurrentType().getDuration());
                        user.getCurrentType().setSecondsGone(0);

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
    if (user.getCurrentType() != null) {
        user.setSelectedName((String) (user.getCurrentType().getName()));
    }

    user.setCurrentSelect(null);
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
