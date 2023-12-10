package main.commands.player.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Song;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;
import java.util.Iterator;

public class RemoveAlbum implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    private String message;


    public void execute(User user, Artist artist, Host host, ArrayList<User> users,
                        ArrayList<Song> songs, ArrayList<Album> albums) {
        this.setRemoveAlbum(user, artist, host, users, songs, albums);
    }


    public void setRemoveAlbum(User user, Artist artist, Host host, ArrayList<User> users,
                               ArrayList<Song> songs, ArrayList<Album> albums) {


        if (user != null || host != null) {
            this.message = this.user + " is not an artist.";
            return;
        } else if (artist == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }


//        verifying if the album exists
        Album albumToRemove = null;
        boolean exists = false;
        for (Album album : artist.getAlbums()) {
            if (album.getName().equals(this.name)) {
                albumToRemove = album;
                exists = true;
                break;
            }
        }
        if (!exists) {
            this.setMessage(this.user + " doesn't have an album with the given name.");
            return;
        }


//        verifying if a users currently listens to the album
        for (User currentUser : users) {
            if (currentUser.getCurrentType() != null) {
                if (currentUser.getCurrentPlaylist().getName().equals(this.name)) {
                    this.setMessage(this.user + " can't delete this album.");
                    return;
                }

//                if the current playlist contains some of the songs we want to delete
                for (Song s : currentUser.getCurrentPlaylist().getSongList()) {

                    for (Song albumSong : albumToRemove.getAlbumSongs()) {
                        if (s.getName().equals(albumSong.getName()) && s.getAlbum().equals(albumSong.getAlbum())) {
                            this.setMessage(this.user + " can't delete this album.");
                            return;
                        }
                    }
                }
            }
        }


//        removing the album
        for (Album album : artist.getAlbums()) {
            if (album.getName().equals(this.name)) {
                artist.getAlbums().remove(album);
                albums.remove(album);

//                deleting also every song from the album
                songs.removeAll(album.getAlbumSongs());
                for (User u : users) {
                    u.getLikedSongs().removeAll(album.getAlbumSongs());

                    for (Playlist playlist : u.getPlayListList()) {

                        Iterator<Song> iterator = playlist.getSongList().iterator();
                        while (iterator.hasNext()) {
                            Song song = iterator.next();
                            for (Song albumSong : album.getAlbumSongs()) {
                                if (song.getName().equals(albumSong.getName()) && song.getAlbum().equals(albumSong.getAlbum())) {
                                    iterator.remove();
                                    playlist.getSongs().remove(song.getName());

                                    break;
                                }
                            }
                        }

                    }

                }

                break;
            }
        }

        this.setMessage(this.user + " deleted the album successfully.");


    }


    public RemoveAlbum(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
    }


    @Override
    public void accept(CommandVisitor visitor) {
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

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
