package main.commands.searchBar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.users.Artist;
import main.users.Host;

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
    private static final int PODCAST = 1;
    private static final int PLAYLIST = 2;
    private static final int ALBUM = 3;
    private static final int ARTIST = 4;
    private static final int HOST = 5;

    /**
     * This method is used to execute the command.
     */
    public void execute(final User currUser, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts,
                        final ArrayList<Album> albums,
                        final ArrayList<Artist> artists,
                        final ArrayList<Host> hosts) {

        if (!currUser.getOnline()) {
            this.setMessage(this.user + " is offline.");
            return;
        }

        this.setSearch(currUser, everyPlaylist, podcasts, albums, artists, hosts);
    }


    /**
     * This method is used to accept the visitor.
     * @param visitor the visitor
     */
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

        ArrayList<String> result = new ArrayList<>();

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
                result.add(song.getName());
            }

//            maximum size of 5
            if (result.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(result);
        this.setMessage("Search returned " + result.size() + " results");
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
                && (name == null || playlist.getName().startsWith(name))) {
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
     * searching by album type
     * @param albums every album
     */
    public void searchingByAlbum(final ArrayList<Album> albums) {
        String name = (String) (filters.get("name"));
        String owner = (String) (filters.get("owner"));
        String description = (String) (filters.get("description"));

        for (Album album : albums) {
            if ((name == null || album.getName().equals(name))
                    && (owner == null || album.getUser().equals(owner))
                    && (description == null || album.getDescription().equals(description))) {
                results.add(album.getName());
            }

            if (results.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(results);
        this.setMessage("Search returned " + results.size() + " results");
    }


    /**
     * searching by artist type
     * @param artists every artist
     */
    public void searchingByArtist(final ArrayList<Artist> artists) {
        String name = (String) (filters.get("name"));

        for (Artist artist : artists) {
            if (artist.getUsername().startsWith(name)) {
                results.add(artist.getUsername());
            }

            if (results.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(results);
        this.setMessage("Search returned " + results.size() + " results");
    }

    /**
     * searching by host type
     * @param hosts every host
     */
    public void searchingByHost(final ArrayList<Host> hosts) {
        String name = (String) (filters.get("name"));

        for (Host host : hosts) {
            if (host.getUsername().startsWith(name)) {
                results.add(host.getUsername());
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
     * @param currUser the current user
     * @param everyPlaylist every playlist
     * @param podcasts every podcast
     */
    public void setSearch(final User currUser, final ArrayList<Playlist> everyPlaylist,
                          final ArrayList<Podcast> podcasts,
                          final ArrayList<Album> albums,
                          final ArrayList<Artist> artists,
                          final ArrayList<Host> hosts) {

//                if only type is songs:
        if (this.type.equals("song")) {
            this.searchingBySongType(currUser.getEverySong());
            currUser.setTypeFoundBySearch(0);
        }

//                if only type is podcasts:
        if (this.type.equals("podcast")) {
            this.searchingByPodcastType(podcasts);
            currUser.setTypeFoundBySearch(PODCAST);
        }

//                if only type is playlist:
        if (this.type.equals("playlist")) {
            this.searchingByPlaylistType(everyPlaylist);
            currUser.setTypeFoundBySearch(PLAYLIST);
        }

//        if only type is album
        if (this.type.equals("album")) {
            this.searchingByAlbum(albums);
            currUser.setTypeFoundBySearch(ALBUM);
        }

//        if only type is artist:
        if (this.type.equals("artist")) {
            this.searchingByArtist(artists);
            currUser.setTypeFoundBySearch(ARTIST);
        }

//        if only type is album
        if (this.type.equals("host")) {
            this.searchingByHost(hosts);
            currUser.setTypeFoundBySearch(HOST);
        }


        currUser.setCurrentSearch(this);
        currUser.setTypeSelected(-1);
        currUser.setCurrentType(null);
        currUser.setCurrentPodcast(null);
        currUser.setCurrentPlaylist(null);

        currUser.setTypeLoaded(-1);
        currUser.setShuffle(false);


        currUser.setTypeLoaded(-1);
        currUser.setRepeatString("No Repeat");
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
