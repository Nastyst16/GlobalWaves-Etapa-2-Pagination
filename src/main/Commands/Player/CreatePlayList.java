package main.Commands.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.Commands.Types.Playlist;
import main.Commands.Types.Podcast;
import main.Commands.Types.Song;
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
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public CreatePlayList(final String command, final String user, final int timestamp,
                          final String playlistName, final String message) {

        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        this.message = message;

        this.playlist = new Playlist(playlistName, user);
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

    /**
     * @return the songs
     */
    @Override
    public void execute(final ArrayList<Command> commands, final SearchBar input,
                        final User user, final ArrayList<Song> songs,
                        final ArrayList<Playlist> everyPlaylist,
                        final ArrayList<Podcast> podcasts) {


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
}
