package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.SearchBar;
import main.User;

import java.util.ArrayList;

public class CreatePlayList implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String playlistName;
    private String message;
    private  Playlist playlist;


    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input, final User user,
                        final ArrayList<Song> songs, final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts, final ArrayList<User> users,
                        final ArrayList<Album> albums) {

//        if the user is offline
        if (user.getOnline() == false) {
            this.message = this.user + " is offline.";
            return;
        }

//                verify if a playlist with the same name exists;
        String message = "Playlist created successfully.";
        for (Playlist playlist : everyPlaylist) {
            if (playlist.getName().equals(input.getPlaylistName())) {
                message = "A playlist with the same name already exists.";
            }
        }

        this.setMessage(message);

        if (!message.equals("A playlist with the same name already exists.")) {
            user.addPlaylistToList(this.getPlaylist());
            everyPlaylist.add(this.getPlaylist());
        }

    }


    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public CreatePlayList(final SearchBar input) {

        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.message = null;

        this.playlist = new Playlist(input.getPlaylistName(), user);
    }


    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * @return the playlistName
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     * @param playlistName the playlistName to set
     */
    @JsonIgnore
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return playlist
     */
    public Playlist getPlaylist() {
        return playlist;
    }

    /**
     * @param playlist the playlist to set
     */
    @JsonIgnore
    public void setPlaylist(final Playlist playlist) {
        this.playlist = playlist;
    }


}
