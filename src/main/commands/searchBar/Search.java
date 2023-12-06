package main.commands.searchBar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Search implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final String type;
    private final Map<String, Object> filters;

    private String message;
    private ArrayList<String> results;
    private static final int MAX_SIZE = 5;

    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }


    /**
     * This is the constructor of the Search class.
     * @param input the input
     */
    public Search(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.type = input.getType();
        this.filters = input.getFilters();
        this.setMessage("Please conduct a search before making a selection");

//        initialize the results array
        this.results = new ArrayList<>();
    }


    /**
     * searching by song type
     * @param songs every song
     */
    public void searchingBySongType(final ArrayList<Song> songs) {
        String songPrefix = (String) (filters.get("name"));
        String album = (String) (filters.get("album"));
        String lyrics = (String) (filters.get("lyrics"));
//        converting to lowerCase
        if (lyrics != null) {
            lyrics = lyrics.toLowerCase();
        }
        String genre = (String) (filters.get("genre"));
        List<String> tags = (List<String>) (filters.get("tags"));
        String releaseYear = (String) (filters.get("releaseYear"));
        String operator = null;
        int targetYear = 0;

        if (releaseYear != null) {
//              operator can be <, >, or =
            operator = releaseYear.substring(0, 1);
//              target year is the year that the user wants to compare to
            targetYear = Integer.parseInt(releaseYear.substring(1));
        }

        String artist = (String) (filters.get("artist"));

        ArrayList<String> results = new ArrayList<>();

        for (Song song : songs) {
            String songLyrics = song.getLyrics().toLowerCase();
//            if the song matches the filters, add it to the results
            if ((songPrefix == null || song.getName().startsWith(songPrefix))
                    && (album == null || song.getAlbum().equals(album))
                    && (lyrics == null || songLyrics.contains(lyrics))
                    && (genre == null || song.getGenre().equalsIgnoreCase(genre))
                    && (tags == null || song.getTags().containsAll(tags))
                    && (artist == null || song.getArtist().equals(artist))
                    && (releaseYear == null || (operator.equals("<")
                    && song.getReleaseYear() < targetYear)
                    || (operator.equals(">") && song.getReleaseYear() > targetYear)
                    || (operator.equals("=") && song.getReleaseYear() == targetYear))) {
                results.add(song.getName());
            }

//            maximum size of 5
            if (results.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(results);
        this.setMessage("Search returned " + results.size() + " results");
    }

    /**
     * searching by podcast type
     * @param podcasts every podcast
     */
    public void searchingByPodcastType(final ArrayList<Podcast> podcasts) {

        String podcastPrefix = (String) (filters.get("name"));
        String owner = (String) (filters.get("owner"));

        for (Podcast podcast : podcasts) {
            if ((podcastPrefix == null || podcast.getName().startsWith(podcastPrefix))
                    && (owner == null || podcast.getOwner().equals(owner))) {
                results.add(podcast.getName());
            }

            if (results.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(results);
        this.setMessage("Search returned " + results.size() + " results");
    }

    /**
     * searching by playlist type
     * @param everyPlaylist every playlist
     */
    public void searchingByPlaylistType(final ArrayList<Playlist> everyPlaylist) {
        String owner = (String) (filters.get("owner"));
        String name = (String) (filters.get("name"));

        for (Playlist playlist : everyPlaylist) {
            if (playlist.getVisibility().equals("private") && !playlist.getUser().equals(user)) {
                continue;
            }
            if ((owner == null || playlist.getUser().equals(owner))
                && (name == null || playlist.getName().equals(name))) {
                results.add(playlist.getName());
            }

            if (results.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(results);

        this.setMessage("Search returned " + results.size() + " results");
    }

    /**
     * This method is used to set the search.
     * @param user the current user
     * @param songs every song
     * @param everyPlaylist every playlist
     * @param podcasts every podcast
     */
    public void setSearch(final User user, final ArrayList<Song> songs,
                          final ArrayList<Playlist> everyPlaylist,
                          final ArrayList<Podcast> podcasts) {

//                if only type is songs:
        if (this.type.equals("song")) {
            this.searchingBySongType(user.getEverySong());
            user.setTypeFoundBySearch(0);
        }

//                if only type is podcasts:
        if (this.type.equals("podcast")) {
            this.searchingByPodcastType(podcasts);
            user.setTypeFoundBySearch(1);
        }

//                if only type is playlist:
        if (this.type.equals("playlist")) {
            this.searchingByPlaylistType(everyPlaylist);
            user.setTypeFoundBySearch(2);
        }

        user.setCurrentSearch(this);
        user.setTypeSelected(-1);
        user.setCurrentType(null);
        user.setTypeLoaded(-1);
        user.setRepeatString("No Repeat");
    }

    /**
     * This method is used to execute the command.
     */
    public void execute(final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {

        if (user.getOnline() == false) {
            this.setMessage(this.user + " is offline.");
            return;
        }

        this.setSearch(user, songs, everyPlaylist, podcasts);
    }

    /**
     * This method is used to get the user of the command.
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * This method is used to get the timestamp of the command.
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * This method is used to get the message of the command.
     * @return the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * This method is used to get the message of the command.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method is used to get the results of the command.
     * @return the results
     */
    public ArrayList<String> getResults() {
        return results;
    }

    /**
     * This method is used to set the results of the command.
     * @param results the results
     */
    public void setResults(final ArrayList<String> results) {
        this.results = results;
    }

    /**
     * This method is used to get the command of the command.
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * This method is used to get the type of the command.
     * @return the type
     */
    @JsonIgnore
    public String getType() {
        return type;
    }

    /**
     * This method is used to get the filters of the command.
     * @return the filters
     */
    @JsonIgnore
    public Map<String, Object> getFilters() {
        return filters;
    }

}
