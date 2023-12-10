package main.commands.player.admin;

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
import java.util.Iterator;

public final class DeleteUser implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * method that executes the DeleteUser command
     * and calls the setDeleteUser method
     */
    public void execute(final ArrayList<User> users, final ArrayList<Artist> artists,
                        final ArrayList<Host> hosts, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Album> albums,
                        final ArrayList<Podcast> podcasts) {
        this.setDeleteUser(users, artists, hosts, songs, everyPlaylist, albums, podcasts);
    }

    /**
     * Constructor for the DeleteUser class
     * @param input the input given by the user
     */
    public DeleteUser(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Method that deletes a user, artist or host
     */
    public void setDeleteUser(final ArrayList<User> users, final ArrayList<Artist> artists,
                              final ArrayList<Host> hosts, final ArrayList<Song> songs,
                              final ArrayList<Playlist> everyPlaylist,
                              final ArrayList<Album> albums,
                              final ArrayList<Podcast> podcasts) {

//        finding the user
        User currUser = findUser(users);
        Artist artist = findArtist(artists);
        Host host = findHost(hosts);

//        if we couldn't find anything above
        if (currUser == null && artist == null && host == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
            return;
        }

//        deleting the user, artist or host depending on what we found
        if (currUser != null) {
            this.delUser(currUser, users, everyPlaylist);
        } else if (artist != null) {
            this.delArtist(artist, users, songs, albums);
        } else if (host != null) {
            this.delHost(host, users, podcasts);
        }
    }

    /**
     * Method that finds a user
     * @param users the list of users
     * @return the user if found, null otherwise
     */
    public User findUser(final ArrayList<User> users) {
        User currUser = null;
        for (User u : users) {
            if (this.getUser().equals(u.getUsername())) {
                currUser = u;
                break;
            }
        }

        return currUser;
    }

    /**
     * Method that finds an artist
     * @param artists the list of artists
     * @return the artist if found, null otherwise
     */
    public Artist findArtist(final ArrayList<Artist> artists) {
        Artist artist = null;
        for (Artist a : artists) {
            if (this.getUser().equals(a.getUsername())) {
                artist = a;
                break;
            }
        }

        return artist;
    }

    /**
     * Method that finds a host
     * @param hosts the list of hosts
     * @return the host if found, null otherwise
     */
    public Host findHost(final ArrayList<Host> hosts) {
        Host host = null;
        for (Host h : hosts) {
            if (this.getUser().equals(h.getUsername())) {
                host = h;
                break;
            }
        }

        return host;
    }

    /**
     * Method that deletes a user and everything related to it,
     * we also verify if the user is listening to one of the user playlists
     * if he is, we can't delete the user
     *
     * @param currUser the user to be deleted
     * @param users the list of users
     * @param everyPlaylist the list of every playlist
     */
    public void delUser(final User currUser, final ArrayList<User> users,
                        final ArrayList<Playlist> everyPlaylist) {

        if (currUser == null) {
            this.setMessage(this.user + " doesn't exist.");
            return;
        }

//        verifying if a user is listening to one of the user playlists
        for (User currentUser : users) {
            for (Playlist playlist : currUser.getPlayListList()) {
                if (currentUser.getCurrentPlaylist() != null
                        && currentUser.getCurrentPlaylist().getName().equals(playlist.getName())) {
                    this.setMessage(this.user + " can't be deleted.");
                    return;
                }
            }
        }

        for (Playlist followedPlaylist : currUser.getFollowedPlaylists()) {
//            remove the follower
            for (Playlist p : everyPlaylist) {
                if (p.getName().equals(followedPlaylist.getName())) {
                    p.setFollowers(p.getFollowers() - 1);
                }
            }

        }

        users.remove(currUser);

        for (User u : users) {
            u.getFollowedPlaylists().removeAll(currUser.getPlayListList());
        }

        this.setMessage(this.user + " was successfully deleted.");
    }

    /**
     * Method that deletes an artist and everything related to it,
     * we also verify if the user is listening to one of the artist songs
     * if he is, we can't delete the artist
     *
     * @param artist the artist to be deleted
     * @param users the list of users
     * @param everySong the list of every song
     * @param albums the list of albums
     */
    public void delArtist(final Artist artist, final ArrayList<User> users,
                          final ArrayList<Song> everySong, final ArrayList<Album> albums) {

        if (artist == null) {
            this.setMessage(this.user + " doesn't exist.");
            return;
        }

        for (User u : users) {

//            if the user selected the artist page
            if (u.getSelectedPageOwner().equals(this.user)) {
                this.setMessage(this.user + " can't be deleted.");
                return;
            }

            String currentlyPlaying = null;
            if (u.getCurrentType() != null) {
                currentlyPlaying = u.getCurrentType().getName();
            }

            for (Album album : artist.getAlbums()) {

                for (Song song : album.getAlbumSongs()) {

                    if (song.getName().equals(currentlyPlaying)) {
                        this.setMessage(artist.getUsername() + " can't be deleted.");
                        return;
                    }
                }
            }
        }

//        deleting everything related to the artist
        for (Album a : artist.getAlbums()) {

            everySong.removeAll(a.getAlbumSongs());
            for (User u : users) {

                u.setEverySong(everySong);
//                deleting also every user liked songs corelated to the artist
                for (Song songToRemove : a.getAlbumSongs()) {

                    for (Song song : u.getLikedSongs()) {

                        if (song.getName().equals(songToRemove.getName())) {
                            u.getLikedSongs().remove(song);
                            break;
                        }
                    }
                }
            }
        }
        albums.removeAll(artist.getAlbums());

        this.setMessage(this.user + " was successfully deleted.");
    }

    /**
     * Method that deletes a host and everything related to it,
     * we also verify if the user is listening to one of the host podcasts
     * if he is, we can't delete the host
     *
     * @param host the host to be deleted
     * @param users the list of users
     * @param podcasts the list of podcasts
     */
    public void delHost(final Host host, final ArrayList<User> users,
                        final ArrayList<Podcast> podcasts) {

        if (host == null) {
            this.setMessage(this.user + " doesn't exist.");
            return;
        }

//        verifying if a user is listening to one of host podcast
        for (User currentUser : users) {

//            if the user selected the host page
            if (currentUser.getSelectedPageOwner().equals(this.user)) {
                this.setMessage(this.user + " can't be deleted.");
                return;
            }

//            if the user is listening to a podcast related to the host
            if (currentUser.getCurrentType() != null) {
                for (Podcast podcast : host.getHostPodcasts()) {

                    if (currentUser.getCurrentPodcast() == null) {
                        continue;
                    }

                    if (podcast.getName().equals(currentUser.getCurrentPodcast().getName())) {
                        this.setMessage(this.user + " can't be deleted.");
                        return;
                    }
                }
            }
        }

//        deleting everything related to the host
        Iterator<Podcast> iterator = podcasts.iterator();
        while (iterator.hasNext()) {
            Podcast podcast = iterator.next();

            for (Podcast podcastToRemove : host.getHostPodcasts()) {

                if (podcast.getName().equals(podcastToRemove.getName())) {
                    iterator.remove();
                    break;
                }
            }
        }
        for (User u : users) {
            u.setEveryPodcast(podcasts);
        }

        this.setMessage(this.user + " was successfully deleted.");
    }

    /**
     * Method that accepts a visitor
     * @param visitor the visitor
     */
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * getter for the message
     * @return the message
     */
    public String getCommand() {
        return command;
    }

    /**
     * getter for the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
