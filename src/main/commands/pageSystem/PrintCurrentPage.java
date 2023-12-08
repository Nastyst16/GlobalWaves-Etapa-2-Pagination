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

            StringBuilder followedPlaylists = new StringBuilder();
            for (Playlist playlist : user.getFollowedPlaylists()) {
                followedPlaylists.append(playlist.getName())
                        .append(" - ")
                        .append(playlist.getUser());

                if (user.getFollowedPlaylists().indexOf(playlist) != user.getFollowedPlaylists().size() - 1) {
                    followedPlaylists.append(", ");
                }
            }


            this.message = "Liked songs:\n\t" + user.getLikedSongs() + "\n\n"
                    + "Followed playlists:\n\t[" + followedPlaylists + "]";
//          if the current page is Artist
        } else if (user.getCurrentPage().equals("Artist")) {
            Artist currentArtist = null;
            for (Artist artist : artists) {
                if (artist.getUsername().equals(user.getSelectedPageOwner())) {
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
        } else if (user.getCurrentPage().equals("Host")) {
            Host currentHost = null;
            for (Host host : hosts) {
                if (host.getUsername().equals(user.getSelectedPageOwner())) {
                    currentHost = host;
                    break;
                }
            }

            StringBuilder podcastsByName = new StringBuilder();
            StringBuilder announcementsByName = new StringBuilder();

            for (Podcast podcast : currentHost.getHostPodcasts()) {
                podcastsByName.append(podcast.getName())
                        .append(":\n\t[");

                for (Episode episode : podcast.getEpisodesList()) {
                    podcastsByName.append(episode.getName())
                            .append(" - ")
                            .append(episode.getDescription());

                    if (podcast.getEpisodesList().indexOf(episode) != podcast.getEpisodes().size() - 1) {
                        podcastsByName.append(", ");
                    }
                }

                if (currentHost.getHostPodcasts().indexOf(podcast) != currentHost.getHostPodcasts().size() - 1) {
                    podcastsByName.append("]\n, ");
                }
            }
            podcastsByName.append("]");

            for (Announcement announcement : currentHost.getAnnouncements()) {
                announcementsByName.append(announcement.getName())
                        .append(":\n\t")
                        .append(announcement.getDescription())
                        .append("\n");

                if (currentHost.getAnnouncements().indexOf(announcement) != currentHost.getAnnouncements().size() - 1) {
                    announcementsByName.append(", ");
                }
            }

            this.message = "Podcasts:\n\t[" + podcastsByName + "\n]\n\n"
                    + "Announcements:\n\t[" + announcementsByName + "]";
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
