package main.commands.player.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.*;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;

public class AddAlbum implements Command {

    private final String command;
    private final String user;
    private final int timestamp;
    private final String name;
    private final int releaseYear;
    private final String description;
    private final ArrayList<Song> albumSongs;
    private String message;


    public AddAlbum(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.releaseYear = input.getReleaseYear();
        this.description = input.getDescription();
        this.albumSongs = input.getSongs();
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }


    public void execute(User user, Artist artist, Host host, ArrayList<Song> songs, ArrayList<User> users,
                        ArrayList<Artist> artists, ArrayList<Album> everyAlbum) {

        this.addAlbum(user, artist, host, songs, everyAlbum, artists, users);
    }

    public void addAlbum(final User user, final Artist artist, final Host host, final ArrayList<Song> everySong,
                         final ArrayList<Album> everyAlbum, final ArrayList<Artist> artists,
                         final ArrayList<User> users) {

        if (user != null || host != null) {
            this.message = this.user + " is not an artist.";
            return;
        } else if (artist == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

//        verifying if the album already exists
        for (Album album : artist.getAlbums()) {
            if (album.getName().equals(this.name)) {
                this.message = this.user + " has another album with the same name.";
                return;
            }
        }

//        verifying if the album has duplicate songs
        for (int i = 0; i < albumSongs.size(); ++i) {
            for (int j = i + 1; j < albumSongs.size(); ++j) {
                if (albumSongs.get(i).getName().equals(albumSongs.get(j).getName())) {
                    this.message = this.user + " has the same song at least twice in this album.";
                    return;
                }
            }
        }


        for (Song song : albumSongs) {
            everySong.add(song);
        }
        for (User u : users) {
            u.setEverySong(everySong);
        }

        everyAlbum.add(new Album(this.user ,this.name, this.releaseYear, this.description, this.albumSongs));
        artist.getAlbums().add(everyAlbum.get(everyAlbum.size() - 1));
        this.message = this.user + " has added new album successfully.";
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

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public int getReleaseYear() {
        return releaseYear;
    }

    @JsonIgnore
    public String getDescription() {
        return description;
    }

    @JsonIgnore
    public ArrayList<Song> getAlbumSongs() {
        return albumSongs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
