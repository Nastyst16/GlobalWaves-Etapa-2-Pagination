package main.commands.player.admin;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.users.Artist;

import java.util.ArrayList;

public class ShowAlbums implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private ArrayList<Album> result;


    public void execute(final Artist artist) {
        this.setShowAlbums(artist.getArtistAlbums());
    }

    public void setShowAlbums(final ArrayList<Album> userAlbums) {
        for (Album album : userAlbums) {
            this.result.add(album);
        }
    }


    public ShowAlbums(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();

        this.result = new ArrayList<>();
    }

    @Override
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

    public ArrayList<Album> getResult() {
        return result;
    }

    public void setResult(ArrayList<Album> result) {
        this.result = result;
    }
}
