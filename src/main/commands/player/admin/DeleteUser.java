package main.commands.player.admin;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.Album;
import main.commands.types.Song;
import main.users.Artist;
import main.users.Host;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DeleteUser implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    public void execute(ArrayList<User> users, ArrayList<Artist> artists, ArrayList<Host> hosts) {
        this.setDeleteUser(users, artists, hosts);
    }


    public DeleteUser(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }


    public void setDeleteUser(ArrayList<User> users, ArrayList<Artist> artists, ArrayList<Host> hosts) {

//        finding the user
        User user = findUser(users);
        Artist artist = findArtist(artists);
        Host host = findHost(hosts);

//        if we couldn't find anything above
        if (user == null && artist == null && host == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
            return;
        }


//        if (user != null) {
//
//
//
//        } else if (artist != null) {
//
//
//        } else if (host != null) {
//
//        }

        this.delArtist(artist, users);



        int debug = 5;
    }

    public User findUser(ArrayList<User> users) {
        User user = null;
        for (User u : users) {
            if (this.getUser().equals(u.getUsername())) {
                user = u;
                break;
            }
        }

        return user;
    }

    public Artist findArtist(ArrayList<Artist> artists) {
        Artist artist = null;
        for (Artist a : artists) {
            if (this.getUser().equals(a.getUsername())) {
                artist = a;
                break;
            }
        }

        return artist;
    }
    public Host findHost(ArrayList<Host> hosts) {
        Host host = null;
        for (Host h : hosts) {
            if (this.getUser().equals(h.getUsername())) {
                host = h;
                break;
            }
        }

        return host;
    }


    public void delUser(User user, ArrayList<User> users) {

//        for (User u : users) {
//            if (u.getSelectedName().equals())
//        }

    }

    public void delArtist(Artist artist, ArrayList<User> users) {

        if (artist == null) {
            return;
        }

        for (User u : users) {

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
    }


    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    public String getCommand() {
        return command;
    }

    public String getUser() {
        return user;
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
