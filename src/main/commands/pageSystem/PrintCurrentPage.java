package main.commands.pageSystem;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.*;
import main.users.Artist;
import main.users.Host;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PrintCurrentPage implements Command {

    private final String user;
    private final String command;
    private final int timestamp;
    private String message;

    public void execute(final User user, final ArrayList<Artist> artists, final ArrayList<Host> hosts) {
        this.setPrintCurrPage(user, artists, hosts);
    }

    public void setPrintCurrPage(User user, ArrayList<Artist> artists, ArrayList<Host> hosts) {

//        if the user is offline
        if (!user.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

//        if the current page is Home
        if (user.getCurrentPage().equals("Home")) {

            ArrayList<String> likedSongs = new ArrayList<>();
            ArrayList<String> followedPlaylists = new ArrayList<>();

            for (Song song : user.getLikedSongs()) {
                likedSongs.add(song.getName());
            }
            for (Playlist playlist : user.getFollowedPlaylists()) {
                followedPlaylists.add(playlist.getName());
            }

            this.message = "Liked songs:\n\t" + likedSongs + "\n\n"
                    + "Followed playlists:\n\t" + followedPlaylists;
//          if the current page is LikedContent
        } else if (user.getCurrentPage().equals("LikedContent")) {
            this.message = "Liked songs:\n\t" + user.getLikedSongs() + "\n\n"
                    + "Followed playlists:\n\t" + user.getFollowedPlaylists();
//          if the current page is Artist
        } else if (user.getCurrentPage().equals("Artist")) {
            Artist currentArtist = null;
            for (Artist artist : artists) {
                if (artist.getUsername().equals(user.getSelectedName())) {
                    currentArtist = artist;
                    break;
                }
            }

            ArrayList<String> albumsByName = new ArrayList<>();
            StringBuilder merchByName = new StringBuilder();
            StringBuilder eventsBuilder = new StringBuilder();

            for (Album album : currentArtist.getAlbums()) {
                albumsByName.add(album.getName());
            }
            for (Merch merch : currentArtist.getMerchandise()) {
                merchByName.append(merch.getName())
                        .append(" - ")
                        .append(merch.getPrice())
                        .append(":\n\t")
                        .append(merch.getDescription());

                if (currentArtist.getMerchandise().indexOf(merch) != currentArtist.getMerchandise().size() - 1) {
                    merchByName.append(", ");
                }
            }

            for (Event event : currentArtist.getEvents()) {
                // if it's the last event -> to avoid the ", " at the end of the last event
                eventsBuilder.append(event.getName())
                        .append(" - ")
                        .append(event.getDate())
                        .append(":\n\t")
                        .append(event.getDescription());

                if (currentArtist.getEvents().indexOf(event) != currentArtist.getEvents().size() - 1) {
                    eventsBuilder.append(", ");
                }
            }

            this.message = "Albums:\n\t" + albumsByName + "\n\n"
                    + "Merch:\n\t[" + merchByName + "]\n\n"
                    + "Events:\n\t[" + eventsBuilder.toString() + "]";
//          if the current page is Host
        }

    }

    public PrintCurrentPage(final SearchBar input) {
        this.user = input.getUsername();
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
    }


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public String getUser() {
        return user;
    }
    public String getCommand() {
        return command;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
